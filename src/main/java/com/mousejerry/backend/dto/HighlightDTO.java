package com.mousejerry.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HighlightDTO {
    private Long id;
    private String gameId;
    private String gameName;
    private String customImage;
    private String buttonColor;
    private LocalDateTime createdAt;
}
