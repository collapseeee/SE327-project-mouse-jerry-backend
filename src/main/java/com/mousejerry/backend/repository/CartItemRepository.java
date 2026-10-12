package com.mousejerry.backend.repository;

import com.mousejerry.backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUserIdOrderByCreatedAtAsc(Long userId);

    Optional<CartItem> findByUserIdAndGameId(Long userId, String gameId);

    void deleteByUserIdAndGameId(Long userId, String gameId);

    void deleteByUserId(Long userId);

    long countByUserId(Long userId);
}
