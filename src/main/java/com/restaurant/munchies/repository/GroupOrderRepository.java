package com.restaurant.munchies.repository;

import com.restaurant.munchies.entities.GroupOrder;
import com.restaurant.munchies.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupOrderRepository extends JpaRepository<GroupOrder, UUID> {
//    GroupOrder findByUUID(UUID uuid);
}
