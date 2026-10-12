package com.mousejerry.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "order_item_keys")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Order order;

    @Column(name = "game_id")
    private String gameId;

    @Column(name = "game_title", nullable = false)
    private String gameTitle;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "game_key", nullable = false)
    private String gameKey;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal price = BigDecimal.ZERO;
}
