package com.restaurant.munchies.services;

import com.restaurant.munchies.dto.GroupOrderDTO;
import com.restaurant.munchies.entities.GroupOrder;
import com.restaurant.munchies.entities.Orders;

import java.util.List;
import java.util.UUID;

public interface GroupOrderService {
    public GroupOrder saveOrUpdate(GroupOrder groupOrder);
    public void delete(GroupOrder groupOrder);
    List<GroupOrder> listAllGroupOrder();
    public GroupOrder findOne(UUID id);
   // GroupOrder findByUUID(UUID uuid);
    GroupOrder findById(UUID id);
    List<GroupOrder> listActiveGroupOrder(UUID id);
    void setEmailTimer(long delay, GroupOrder groupOrder);
}
