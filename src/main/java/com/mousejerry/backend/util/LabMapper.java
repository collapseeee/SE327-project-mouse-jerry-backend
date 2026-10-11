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
import org.hibernate.Hibernate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
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

    @Mapping(target = "category", expression = "java(mapPrimaryCategoryName(game))")
    @Mapping(target = "categories", expression = "java(mapCategoryNames(game))")
    @Mapping(target = "screenshots", expression = "java(mapScreenshotUrls(game))")
    GameDTO toGameDTO(Game game);

    List<GameDTO> toGameDTOList(List<Game> games);

    default String mapPrimaryCategoryName(Game game) {
        if (game == null || game.getPrimaryCategory() == null) {
            return null;
        }
        if (!Hibernate.isInitialized(game.getPrimaryCategory())) {
            return null;
        }
        return game.getPrimaryCategory().getName();
    }

    default List<String> mapCategoryNames(Game game) {
        if (game == null || game.getCategories() == null) {
            return Collections.emptyList();
        }
        if (!Hibernate.isInitialized(game.getCategories())) {
            return Collections.emptyList();
        }
        return game.getCategories().stream()
                .map(Category::getName)
                .sorted()
                .toList();
    }

    default List<String> mapScreenshotUrls(Game game) {
        if (game == null || game.getScreenshots() == null) {
            return Collections.emptyList();
        }
        if (!Hibernate.isInitialized(game.getScreenshots())) {
            return Collections.emptyList();
        }
        return game.getScreenshots().stream()
                .map(com.mousejerry.backend.entity.GameScreenshot::getUrl)
                .toList();
    }
}

