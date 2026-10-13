package com.mousejerry.backend.controller;

import com.mousejerry.backend.dto.PresignedUrlDTO;
import com.mousejerry.backend.dto.StorageFileDTO;
import com.mousejerry.backend.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class UploadController {

    private final StorageService storageService;

    @PostMapping("/image")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StorageFileDTO> uploadImage(@RequestParam("file") MultipartFile file) {
        StorageFileDTO result = storageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/presigned-url")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PresignedUrlDTO> getPresignedUrl(
            @RequestParam String filename,
            @RequestParam(defaultValue = "image/jpeg") String contentType
    ) {
        PresignedUrlDTO result = storageService.generatePresignedUploadUrl(filename, contentType);
        return ResponseEntity.ok(result);
    }
}
