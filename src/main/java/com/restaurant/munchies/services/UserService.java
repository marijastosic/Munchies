package com.restaurant.munchies.services;

import com.restaurant.munchies.entities.User;

public interface UserService {
    public User findUserByEmail(String email);
    public User saveUser(User user);
}
