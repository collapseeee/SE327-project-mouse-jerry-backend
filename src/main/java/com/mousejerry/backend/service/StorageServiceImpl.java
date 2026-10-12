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

    @Value("${aws.s3.public-base-url:https://djorohnniuxhnrjztdfq.storage.supabase.co/storage/v1/s3/mouse-jerry}")
    private String publicBaseUrl;

    @Override
    public StorageFileDTO uploadImage(MultipartFile file) {
        validateFile(file);
        String extension = getAndValidateExtension(file.getOriginalFilename());
        String contentType = resolveContentType(file.getContentType(), extension);
        String objectKey = generateObjectKey(file.getOriginalFilename());

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(objectKey)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            log.info("Successfully uploaded image to S3: bucket={}, key={}", bucket, objectKey);
        } catch (IOException e) {
            log.error("Failed to read image bytes for upload: {}", e.getMessage(), e);
            throw new IllegalStateException("Failed to read image file data", e);
        }

        return StorageFileDTO.builder()
                .name(objectKey)
                .publicUrl(buildPublicUrl(objectKey))
                .build();
    }

    @Override
    public PresignedUrlDTO generatePresignedUploadUrl(String filename, String contentType) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be empty");
        }

        String extension = getAndValidateExtension(filename);
        String effectiveContentType = resolveContentType(contentType, extension);
        String objectKey = generateObjectKey(filename);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .contentType(effectiveContentType)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(PRESIGNED_URL_EXPIRATION)
                .putObjectRequest(putObjectRequest)
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
        String base = publicBaseUrl != null ? publicBaseUrl.replaceAll("/+$", "") : "";
        return base + "/" + key;
    }
}
