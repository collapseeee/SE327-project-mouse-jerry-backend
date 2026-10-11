package com.mousejerry.backend.dao;

import com.mousejerry.backend.entity.User;

import java.util.Optional;

public interface UserDao {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
