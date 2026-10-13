package com.mousejerry.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemRequirementsDTO {
    private String os;
    private String processor;
    private String memory;
    private String graphics;
    private String storage;
}
