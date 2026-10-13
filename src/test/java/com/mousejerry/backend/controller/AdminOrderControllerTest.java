package com.mousejerry.backend.controller;

import com.mousejerry.backend.config.GlobalExceptionHandler;
import com.mousejerry.backend.dto.OrderDTO;
import com.mousejerry.backend.dto.OrderItemKeyDTO;
import com.mousejerry.backend.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AdminOrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private AdminOrderController adminOrderController;

    private OrderDTO sampleOrder;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminOrderController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        sampleOrder = OrderDTO.builder()
                .id(1L)
                .orderNumber("ORD-98765432")
                .userId(5L)
                .username("jane_doe")
                .totalAmount(BigDecimal.valueOf(1499.00))
                .purchaseDate(LocalDateTime.now())
                .items(List.of(
                        OrderItemKeyDTO.builder()
                                .id(10L)
                                .gameTitle("Cyberpunk 2077")
                                .gameKey("CP77-AAAA-BBBB")
                                .price(BigDecimal.valueOf(1499.00))
                                .build()
                ))
                .build();
    }

    @Test
    void testGetAllOrdersSuccess() throws Exception {
        when(orderService.getAllOrders()).thenReturn(List.of(sampleOrder));

        mockMvc.perform(get("/api/v1/admin/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].orderNumber").value("ORD-98765432"))
                .andExpect(jsonPath("$[0].username").value("jane_doe"))
                .andExpect(jsonPath("$[0].totalAmount").value(1499.00))
                .andExpect(jsonPath("$[0].items[0].gameTitle").value("Cyberpunk 2077"))
                .andExpect(jsonPath("$[0].items[0].gameKey").value("CP77-AAAA-BBBB"));

        verify(orderService).getAllOrders();
    }

    @Test
    void testGetAllOrdersEmpty() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/admin/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(orderService).getAllOrders();
    }
}
