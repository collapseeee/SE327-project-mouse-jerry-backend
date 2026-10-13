package com.mousejerry.backend.controller;

import com.mousejerry.backend.config.GlobalExceptionHandler;
import com.mousejerry.backend.dto.AddToCartRequest;
import com.mousejerry.backend.dto.CartDTO;
import com.mousejerry.backend.dto.CartItemDTO;
import com.mousejerry.backend.dto.OrderDTO;
import com.mousejerry.backend.dto.UpdateCartItemRequest;
import com.mousejerry.backend.service.CartService;
import com.mousejerry.backend.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private CartService cartService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private CartController cartController;

    private CartDTO mockCart;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();

        mockCart = CartDTO.builder()
                .items(List.of(
                        CartItemDTO.builder()
                                .id(1L)
                                .gameId("battlefield-3")
                                .gameTitle("Battlefield 3")
                                .unitPrice(BigDecimal.valueOf(1969.00))
                                .quantity(2)
                                .totalPrice(BigDecimal.valueOf(3938.00))
                                .build()
                ))
                .totalItems(2)
                .totalAmount(BigDecimal.valueOf(3938.00))
                .build();
    }

    @Test
    void testGetCartSuccess() throws Exception {
        when(cartService.getCart("john")).thenReturn(mockCart);

        mockMvc.perform(get("/api/v1/cart").principal(() -> "john"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(2))
                .andExpect(jsonPath("$.totalAmount").value(3938.00))
                .andExpect(jsonPath("$.items[0].gameTitle").value("Battlefield 3"));
    }

    @Test
    void testGetCartUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/cart"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAddToCartSuccess() throws Exception {
        AddToCartRequest request = AddToCartRequest.builder()
                .gameId("battlefield-3")
                .quantity(2)
                .build();

        when(cartService.addToCart("john", "battlefield-3", 2)).thenReturn(mockCart);

        mockMvc.perform(post("/api/v1/cart/items")
                        .principal(() -> "john")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalItems").value(2))
                .andExpect(jsonPath("$.items[0].gameId").value("battlefield-3"));
    }

    @Test
    void testAddToCartUnauthorized() throws Exception {
        AddToCartRequest request = AddToCartRequest.builder().gameId("battlefield-3").quantity(1).build();

        mockMvc.perform(post("/api/v1/cart/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testUpdateCartItemSuccess() throws Exception {
        UpdateCartItemRequest request = UpdateCartItemRequest.builder().quantity(3).build();

        when(cartService.updateCartItem("john", "battlefield-3", 3)).thenReturn(mockCart);

        mockMvc.perform(put("/api/v1/cart/items/battlefield-3")
                        .principal(() -> "john")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateCartItemUnauthorized() throws Exception {
        UpdateCartItemRequest request = UpdateCartItemRequest.builder().quantity(3).build();

        mockMvc.perform(put("/api/v1/cart/items/battlefield-3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testRemoveFromCartSuccess() throws Exception {
        when(cartService.removeFromCart("john", "battlefield-3")).thenReturn(mockCart);

        mockMvc.perform(delete("/api/v1/cart/items/battlefield-3").principal(() -> "john"))
                .andExpect(status().isOk());
    }

    @Test
    void testRemoveFromCartUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/v1/cart/items/battlefield-3"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testClearCartSuccess() throws Exception {
        mockMvc.perform(delete("/api/v1/cart").principal(() -> "john"))
                .andExpect(status().isNoContent());

        verify(cartService).clearCart("john");
    }

    @Test
    void testClearCartUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/v1/cart"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testCheckoutCartSuccess() throws Exception {
        OrderDTO mockOrder = OrderDTO.builder()
                .id(10L)
                .orderNumber("ORD-99999999")
                .totalAmount(BigDecimal.valueOf(3938.00))
                .build();

        when(orderService.checkout(eq("john"), isNull())).thenReturn(mockOrder);

        mockMvc.perform(post("/api/v1/cart/checkout").principal(() -> "john"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderNumber").value("ORD-99999999"))
                .andExpect(jsonPath("$.totalAmount").value(3938.00));
    }

    @Test
    void testCheckoutCartUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/cart/checkout"))
                .andExpect(status().isUnauthorized());
    }
}
