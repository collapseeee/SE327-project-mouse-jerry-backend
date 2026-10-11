package com.mousejerry.backend.util;

import com.mousejerry.backend.dto.CategoryDTO;
import com.mousejerry.backend.dto.GameDTO;
import com.mousejerry.backend.dto.OrderDTO;
import com.mousejerry.backend.dto.OrderItemKeyDTO;
import com.mousejerry.backend.dto.UserProfileDTO;
import com.mousejerry.backend.entity.Category;
import com.mousejerry.backend.entity.Game;
import com.mousejerry.backend.entity.Order;
import com.mousejerry.backend.entity.OrderItemKey;
import com.mousejerry.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LabMapper {

    UserProfileDTO toUserProfileDTO(User user);

    OrderDTO toOrderDTO(Order order);

    List<OrderDTO> toOrderDTOList(List<Order> orders);

    OrderItemKeyDTO toOrderItemKeyDTO(OrderItemKey itemKey);

    List<OrderItemKeyDTO> toOrderItemKeyDTOList(List<OrderItemKey> itemKeys);

    CategoryDTO toCategoryDTO(Category category);

    List<CategoryDTO> toCategoryDTOList(List<Category> categories);

    @Mapping(target = "category", expression = "java(game.getPrimaryCategory() != null ? game.getPrimaryCategory().getName() : null)")
    @Mapping(target = "categories", expression = "java(game.getCategories() != null ? game.getCategories().stream().map(com.mousejerry.backend.entity.Category::getName).sorted().toList() : java.util.Collections.emptyList())")
    @Mapping(target = "screenshots", expression = "java(game.getScreenshots() != null ? game.getScreenshots().stream().map(com.mousejerry.backend.entity.GameScreenshot::getUrl).toList() : java.util.Collections.emptyList())")
    GameDTO toGameDTO(Game game);

    List<GameDTO> toGameDTOList(List<Game> games);
}

