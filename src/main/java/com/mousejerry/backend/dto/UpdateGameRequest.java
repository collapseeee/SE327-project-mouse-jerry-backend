package com.mousejerry.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGameRequest {

    @NotBlank(message = "Game name is required")
    private String name;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    private BigDecimal price;

    private String description;

    private String coverImage;

    private String publisher;

    private String releaseDate;

    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    private Boolean isHighlight;

    private Long primaryCategoryId;

    private String category;

    private Set<Long> categoryIds;

    private List<String> categories;

    private List<String> screenshots;

    private SystemRequirementsDTO systemRequirements;
}
