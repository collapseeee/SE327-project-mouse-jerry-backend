package com.mousejerry.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    @Builder.Default
    private List<CartItemDTO> items = new ArrayList<>();

    @Builder.Default
    private Integer totalItems = 0;

    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;
}
