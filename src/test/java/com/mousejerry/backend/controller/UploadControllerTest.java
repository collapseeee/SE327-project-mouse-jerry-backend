package com.mousejerry.backend.controller;

import com.mousejerry.backend.config.GlobalExceptionHandler;
import com.mousejerry.backend.dto.PresignedUrlDTO;
import com.mousejerry.backend.dto.StorageFileDTO;
import com.mousejerry.backend.service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UploadControllerTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private UploadController uploadController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(uploadController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void uploadImage_ValidFile_ReturnsCreated() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.png",
                "image/png",
                "content".getBytes()
        );

        StorageFileDTO dto = StorageFileDTO.builder()
                .name("games/test.png")
                .publicUrl("https://supabase.co/mouse-jerry/games/test.png")
                .build();

        when(storageService.uploadImage(any())).thenReturn(dto);

        mockMvc.perform(multipart("/api/v1/upload/image").file(file))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("games/test.png"))
                .andExpect(jsonPath("$.publicUrl").value("https://supabase.co/mouse-jerry/games/test.png"));
    }

    @Test
    void uploadImage_InvalidFormat_ReturnsBadRequest() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "content".getBytes()
        );

        when(storageService.uploadImage(any()))
                .thenThrow(new IllegalArgumentException("Unsupported image format: txt"));

        mockMvc.perform(multipart("/api/v1/upload/image").file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Unsupported image format: txt"));
    }

    @Test
    void getPresignedUrl_Valid_ReturnsOk() throws Exception {
        PresignedUrlDTO dto = PresignedUrlDTO.builder()
                .uploadUrl("https://supabase.co/upload-url")
                .fileKey("games/test.webp")
                .publicUrl("https://supabase.co/mouse-jerry/games/test.webp")
                .build();

        when(storageService.generatePresignedUploadUrl("test.webp", "image/webp"))
                .thenReturn(dto);

        mockMvc.perform(get("/api/v1/upload/presigned-url")
                        .param("filename", "test.webp")
                        .param("contentType", "image/webp"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uploadUrl").value("https://supabase.co/upload-url"))
                .andExpect(jsonPath("$.fileKey").value("games/test.webp"))
                .andExpect(jsonPath("$.publicUrl").value("https://supabase.co/mouse-jerry/games/test.webp"));
    }

    @Test
    void getPresignedUrl_Invalid_ReturnsBadRequest() throws Exception {
        when(storageService.generatePresignedUploadUrl("bad.sh", "application/x-sh"))
                .thenThrow(new IllegalArgumentException("Unsupported image format: sh"));

        mockMvc.perform(get("/api/v1/upload/presigned-url")
                        .param("filename", "bad.sh")
                        .param("contentType", "application/x-sh"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Unsupported image format: sh"));
    }
}
