package com.mousejerry.backend.service;

import com.mousejerry.backend.dto.PresignedUrlDTO;
import com.mousejerry.backend.dto.StorageFileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp", "gif");
    private static final Duration PRESIGNED_URL_EXPIRATION = Duration.ofMinutes(15);

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket:mouse-jerry}")
    private String bucket;

    @Value("${aws.s3.public-base-url:https://djorohnniuxhnrjztdfq.supabase.co/storage/v1/object/public/mouse-jerry}")
    private String publicBaseUrl;

    @Value("${file.upload-dir:uploads}")
    private String localUploadDir = "uploads";

    @Override
    public StorageFileDTO uploadImage(MultipartFile file) {
        validateFile(file);
        String extension = getAndValidateExtension(file.getOriginalFilename());
        String contentType = resolveContentType(file.getContentType(), extension);
        String objectKey = generateObjectKey(file.getOriginalFilename());

        try {
            return uploadToS3(file, objectKey, contentType);
        } catch (IOException e) {
            log.error("Failed to read image bytes for upload: {}", e.getMessage(), e);
            throw new IllegalStateException("Failed to read image file data", e);
        } catch (Exception s3Ex) {
            log.warn("S3 upload failed ({}). Falling back to local storage.", s3Ex.getMessage());
            return saveLocally(file, objectKey);
        }
    }

    private StorageFileDTO uploadToS3(MultipartFile file, String objectKey, String contentType) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .contentType(contentType)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        log.info("Successfully uploaded image to S3: bucket={}, key={}", bucket, objectKey);

        return StorageFileDTO.builder()
                .name(objectKey)
                .publicUrl(buildPublicUrl(objectKey))
                .build();
    }

    private StorageFileDTO saveLocally(MultipartFile file, String objectKey) {
        try {
            String baseDir = (localUploadDir != null && !localUploadDir.isBlank()) ? localUploadDir : "uploads";
            Path targetDir = Paths.get(baseDir, "games");
            Files.createDirectories(targetDir);

            String filename = objectKey.startsWith("games/") ? objectKey.substring(6) : objectKey;
            Path targetFile = targetDir.resolve(filename).normalize();
            if (!targetFile.startsWith(targetDir.normalize())) {
                throw new IllegalArgumentException("Invalid file key path");
            }

            Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
            String publicUrl = "/api/v1/upload/files/" + objectKey;
            log.info("Successfully saved image locally: path={}, publicUrl={}", targetFile, publicUrl);

            return StorageFileDTO.builder()
                    .name(objectKey)
                    .publicUrl(publicUrl)
                    .build();
        } catch (IOException ex) {
            log.error("Failed to store image file locally: {}", ex.getMessage(), ex);
            throw new IllegalStateException("Failed to store image file data", ex);
        }
    }

    @Override
    public PresignedUrlDTO generatePresignedUploadUrl(String filename, String contentType) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be empty");
        }

        String extension = getAndValidateExtension(filename);
        String effectiveContentType = resolveContentType(contentType, extension);
        String objectKey = generateObjectKey(filename);

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(PRESIGNED_URL_EXPIRATION)
                .putObjectRequest(b -> b.bucket(bucket).key(objectKey).contentType(effectiveContentType))
                .build();

        PresignedPutObjectRequest presignedPutObjectRequest = s3Presigner.presignPutObject(presignRequest);
        String uploadUrl = presignedPutObjectRequest.url().toString();

        return PresignedUrlDTO.builder()
                .uploadUrl(uploadUrl)
                .fileKey(objectKey)
                .publicUrl(buildPublicUrl(objectKey))
                .build();
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload empty or null file");
        }
    }

    private String getAndValidateExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new IllegalArgumentException("Invalid filename. Must contain a valid image extension.");
        }
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase(Locale.ROOT);
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("Unsupported image format: " + extension + ". Allowed: " + ALLOWED_EXTENSIONS);
        }
        return extension;
    }

    private String resolveContentType(String contentType, String extension) {
        if (contentType != null && contentType.startsWith("image/")) {
            return contentType;
        }
        if ("jpg".equalsIgnoreCase(extension) || "jpeg".equalsIgnoreCase(extension)) {
            return "image/jpeg";
        }
        return "image/" + extension.toLowerCase(Locale.ROOT);
    }

    private String generateObjectKey(String originalFilename) {
        String cleanName = sanitizeFilename(originalFilename);
        return "games/" + UUID.randomUUID() + "_" + cleanName;
    }

    private String sanitizeFilename(String filename) {
        if (filename == null || filename.isBlank()) {
            return "image.png";
        }
        return filename.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    private String buildPublicUrl(String key) {
        if (publicBaseUrl == null || publicBaseUrl.isBlank()) {
            return "/" + key;
        }
        String base = publicBaseUrl.stripTrailing();
        while (base.endsWith("/")) {
            base = base.substring(0, base.length() - 1);
        }
        if (base.contains(".storage.supabase.co/storage/v1/s3")) {
            base = base.replace(".storage.supabase.co/storage/v1/s3", ".supabase.co/storage/v1/object/public");
        }
        return base + "/" + key;
    }
}
