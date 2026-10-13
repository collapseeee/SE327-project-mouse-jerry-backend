package com.mousejerry.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PresignedUrlDTO {

    private String uploadUrl;
    private String fileKey;
    private String publicUrl;
}
