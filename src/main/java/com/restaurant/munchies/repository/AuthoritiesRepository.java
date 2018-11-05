package com.restaurant.munchies.repository;

import com.restaurant.munchies.entities.Authorities;
import com.restaurant.munchies.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {
    Authorities findByAuthority(String authority);
}
