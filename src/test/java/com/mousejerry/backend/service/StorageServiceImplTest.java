package com.mousejerry.backend.service;

import com.mousejerry.backend.dto.PresignedUrlDTO;
import com.mousejerry.backend.dto.StorageFileDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageServiceImplTest {

    @Mock
    private S3Client s3Client;

    @Mock
    private S3Presigner s3Presigner;

    @Mock
    private PresignedPutObjectRequest presignedPutObjectRequest;

    private StorageServiceImpl storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl(s3Client, s3Presigner);
        ReflectionTestUtils.setField(storageService, "bucket", "mouse-jerry");
        ReflectionTestUtils.setField(storageService, "publicBaseUrl", "https://supabase.co/storage/v1/s3/mouse-jerry");
    }

    @Test
    void uploadImage_ValidPng_Success() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test-cover.png",
                "image/png",
                "dummy image content".getBytes()
        );

        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
                .thenReturn(PutObjectResponse.builder().build());

        StorageFileDTO result = storageService.uploadImage(file);

        assertNotNull(result);
        assertTrue(result.getName().startsWith("games/"));
        assertTrue(result.getName().endsWith("test-cover.png"));
        assertTrue(result.getPublicUrl().startsWith("https://supabase.co/storage/v1/s3/mouse-jerry/games/"));
        verify(s3Client, times(1)).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    void uploadImage_ValidJpgFallbackContentType_Success() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "game.jpg",
                null,
                "dummy content".getBytes()
        );

        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
                .thenReturn(PutObjectResponse.builder().build());

        StorageFileDTO result = storageService.uploadImage(file);

        assertNotNull(result);
        assertTrue(result.getName().endsWith("game.jpg"));
    }

    @Test
    void uploadImage_EmptyFile_ThrowsException() {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "test.png", "image/png", new byte[0]);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> storageService.uploadImage(emptyFile));
        assertEquals("Cannot upload empty or null file", ex.getMessage());
    }

    @Test
    void uploadImage_NullFile_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> storageService.uploadImage(null));
        assertEquals("Cannot upload empty or null file", ex.getMessage());
    }

    @Test
    void uploadImage_UnsupportedExtension_ThrowsException() {
        MockMultipartFile file = new MockMultipartFile("file", "malicious.exe", "application/octet-stream", "evil".getBytes());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> storageService.uploadImage(file));
        assertTrue(ex.getMessage().contains("Unsupported image format: exe"));
    }

    @Test
    void uploadImage_NoExtension_ThrowsException() {
        MockMultipartFile file = new MockMultipartFile("file", "badfile", "image/png", "abc".getBytes());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> storageService.uploadImage(file));
        assertTrue(ex.getMessage().contains("Must contain a valid image extension"));
    }

    @Test
    void uploadImage_IoException_ThrowsIllegalStateException() throws IOException {
        MultipartFile failingFile = mock(MultipartFile.class);
        when(failingFile.isEmpty()).thenReturn(false);
        when(failingFile.getOriginalFilename()).thenReturn("error.png");
        when(failingFile.getContentType()).thenReturn("image/png");
        when(failingFile.getInputStream()).thenThrow(new IOException("Simulated disk error"));

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> storageService.uploadImage(failingFile));
        assertEquals("Failed to read image file data", ex.getMessage());
    }

    @Test
    void generatePresignedUploadUrl_ValidFile_Success() throws Exception {
        when(s3Presigner.presignPutObject(any(PutObjectPresignRequest.class)))
                .thenReturn(presignedPutObjectRequest);
        when(presignedPutObjectRequest.url())
                .thenReturn(URI.create("https://supabase.co/presigned-put-url").toURL());

        PresignedUrlDTO result = storageService.generatePresignedUploadUrl("screenshot.webp", "image/webp");

        assertNotNull(result);
        assertEquals("https://supabase.co/presigned-put-url", result.getUploadUrl());
        assertTrue(result.getFileKey().startsWith("games/"));
        assertTrue(result.getFileKey().endsWith("screenshot.webp"));
        assertTrue(result.getPublicUrl().endsWith("screenshot.webp"));
    }

    @Test
    void generatePresignedUploadUrl_EmptyFilename_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> storageService.generatePresignedUploadUrl("", "image/png"));
        assertEquals("Filename cannot be empty", ex.getMessage());
    }

    @Test
    void generatePresignedUploadUrl_UnsupportedExtension_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> storageService.generatePresignedUploadUrl("script.sh", "text/plain"));
        assertTrue(ex.getMessage().contains("Unsupported image format: sh"));
    }
}
