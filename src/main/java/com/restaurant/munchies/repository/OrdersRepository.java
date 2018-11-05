package com.restaurant.munchies.repository;

import com.restaurant.munchies.entities.Orders;
import com.restaurant.munchies.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
}
