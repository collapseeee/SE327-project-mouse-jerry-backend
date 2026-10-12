package com.mousejerry.backend.controller;

import com.mousejerry.backend.dto.AddToCartRequest;
import com.mousejerry.backend.dto.CartDTO;
import com.mousejerry.backend.dto.OrderDTO;
import com.mousejerry.backend.dto.UpdateCartItemRequest;
import com.mousejerry.backend.service.CartService;
import com.mousejerry.backend.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<CartDTO> getCart(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(cartService.getCart(principal.getName()));
    }

    @PostMapping("/items")
    public ResponseEntity<CartDTO> addToCart(@Valid @RequestBody AddToCartRequest request, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CartDTO cart = cartService.addToCart(principal.getName(), request.getGameId(), request.getQuantity());
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @PutMapping("/items/{gameId}")
    public ResponseEntity<CartDTO> updateCartItem(
            @PathVariable String gameId,
            @Valid @RequestBody UpdateCartItemRequest request,
            Principal principal
    ) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CartDTO cart = cartService.updateCartItem(principal.getName(), gameId, request.getQuantity());
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/items/{gameId}")
    public ResponseEntity<CartDTO> removeFromCart(@PathVariable String gameId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CartDTO cart = cartService.removeFromCart(principal.getName(), gameId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        cartService.clearCart(principal.getName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderDTO> checkoutCart(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        OrderDTO order = orderService.checkout(principal.getName(), null);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
