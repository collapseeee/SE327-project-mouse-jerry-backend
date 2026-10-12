package com.mousejerry.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequest {

    /**
     * Checkout type: "cart" (default) to checkout the user's cart items,
     * or "single" to buy a single game directly without touching the cart.
     */
    private String checkoutType;

    /**
     * Required if checkoutType is "single".
     */
    private String gameId;

    /**
     * Quantity for single checkout (defaults to 1).
     */
    @Builder.Default
    private Integer quantity = 1;
}
