package com.mousejerry.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "system_requirements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SystemRequirements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false, unique = true)
    private Game game;

    @Column(name = "os", columnDefinition = "TEXT")
    private String os;

    @Column(name = "processor", columnDefinition = "TEXT")
    private String processor;

    @Column(name = "memory", columnDefinition = "TEXT")
    private String memory;

    @Column(name = "graphics", columnDefinition = "TEXT")
    private String graphics;

    @Column(name = "storage", columnDefinition = "TEXT")
    private String storage;
}
