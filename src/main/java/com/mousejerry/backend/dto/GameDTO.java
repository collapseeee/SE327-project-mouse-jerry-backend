package com.mousejerry.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {
    private String id;
    private String name;
    private BigDecimal price;
    private String coverImage;
    private String category;
    private List<String> categories;
    private String description;
    private List<String> screenshots;
    private String publisher;
    private String releaseDate;
    private Integer stock;
    private Boolean isHighlight;
}
