package com.restaurant.munchies.services.implementation;

import com.restaurant.munchies.entities.Authorities;
import com.restaurant.munchies.entities.User;
import com.restaurant.munchies.repository.AuthoritiesRepository;
import com.restaurant.munchies.repository.UserRepository;
import com.restaurant.munchies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthoritiesRepository authoritiesRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User saveUser(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Authorities authorities = new Authorities();
        authorities = authoritiesRepository.findByAuthority("ADMIN");
        user.setAuthoritiesId(authorities);

        return userRepository.save(user);
    }
}
