package com.mousejerry.backend.util;

import com.mousejerry.backend.dto.OrderDTO;
import com.mousejerry.backend.dto.OrderItemKeyDTO;
import com.mousejerry.backend.dto.UserProfileDTO;
import com.mousejerry.backend.entity.Order;
import com.mousejerry.backend.entity.OrderItemKey;
import com.mousejerry.backend.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LabMapper {

    UserProfileDTO toUserProfileDTO(User user);

    OrderDTO toOrderDTO(Order order);

    List<OrderDTO> toOrderDTOList(List<Order> orders);

    OrderItemKeyDTO toOrderItemKeyDTO(OrderItemKey itemKey);

    List<OrderItemKeyDTO> toOrderItemKeyDTOList(List<OrderItemKey> itemKeys);
}
