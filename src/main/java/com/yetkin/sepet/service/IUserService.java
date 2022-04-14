package com.yetkin.sepet.service;

import com.yetkin.sepet.model.entity.User;

import java.util.Optional;


public interface IUserService {
    User saveUser(User user);

    Optional<User> findByUsername(String username);

    void makeAdmin(String username);
}
