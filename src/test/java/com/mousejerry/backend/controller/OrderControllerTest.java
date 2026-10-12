package com.mousejerry.backend.controller;

import com.mousejerry.backend.config.GlobalExceptionHandler;
import com.mousejerry.backend.dto.CheckoutRequest;
import com.mousejerry.backend.dto.OrderDTO;
import com.mousejerry.backend.dto.OrderItemKeyDTO;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private OrderDTO mockOrder;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();

        mockOrder = OrderDTO.builder()
                .id(10L)
                .orderNumber("ORD-12345678")
                .userId(1L)
                .username("john")
                .totalAmount(BigDecimal.valueOf(1969.00))
                .items(List.of(
                        OrderItemKeyDTO.builder()
                                .id(1L)
                                .gameTitle("Battlefield 3")
                                .gameKey("KEY11-KEY22-KEY33")
                                .price(BigDecimal.valueOf(1969.00))
                                .build()
                ))
                .build();
    }

    @Test
    void testCheckoutSuccess() throws Exception {
        CheckoutRequest request = CheckoutRequest.builder()
                .checkoutType("single")
                .gameId("battlefield-3")
                .quantity(1)
                .build();

        when(orderService.checkout(eq("john"), any(CheckoutRequest.class))).thenReturn(mockOrder);

        mockMvc.perform(post("/api/v1/orders/checkout")
                        .principal(() -> "john")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderNumber").value("ORD-12345678"))
                .andExpect(jsonPath("$.items[0].gameKey").value("KEY11-KEY22-KEY33"));
    }

    @Test
    void testCheckoutUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/orders/checkout"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetOrderHistorySuccess() throws Exception {
        when(orderService.getOrderHistory("john")).thenReturn(List.of(mockOrder));

        mockMvc.perform(get("/api/v1/orders").principal(() -> "john"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].orderNumber").value("ORD-12345678"));
    }

    @Test
    void testGetOrderHistoryUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetOrderByNumberSuccess() throws Exception {
        when(orderService.getOrderByNumber("john", "ORD-12345678")).thenReturn(mockOrder);

        mockMvc.perform(get("/api/v1/orders/ORD-12345678").principal(() -> "john"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").value("ORD-12345678"))
                .andExpect(jsonPath("$.totalAmount").value(1969.00));
    }

    @Test
    void testGetOrderByNumberUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/orders/ORD-12345678"))
                .andExpect(status().isUnauthorized());
    }
}
