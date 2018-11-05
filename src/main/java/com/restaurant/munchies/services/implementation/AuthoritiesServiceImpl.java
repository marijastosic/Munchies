package com.restaurant.munchies.services.implementation;

import com.restaurant.munchies.entities.Authorities;
import com.restaurant.munchies.repository.AuthoritiesRepository;
import com.restaurant.munchies.services.AuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {
    @Autowired
    private AuthoritiesRepository authoritiesRepository;
    @Override
    public void save(Authorities authorities) {
        authoritiesRepository.save(authorities);
    }
}
