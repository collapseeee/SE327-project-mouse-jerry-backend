package com.mousejerry.backend.service;

import com.mousejerry.backend.dto.PresignedUrlDTO;
import com.mousejerry.backend.dto.StorageFileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    StorageFileDTO uploadImage(MultipartFile file);

    PresignedUrlDTO generatePresignedUploadUrl(String filename, String contentType);
}
