package com.restaurant.munchies.services.implementation;

import com.restaurant.munchies.dto.GroupOrderDTO;
import com.restaurant.munchies.entities.GroupOrder;
import com.restaurant.munchies.entities.Orders;
import com.restaurant.munchies.entities.Restaurant;
import com.restaurant.munchies.repository.GroupOrderRepository;
import com.restaurant.munchies.repository.OrdersRepository;
import com.restaurant.munchies.services.GroupOrderService;
import com.restaurant.munchies.services.MailService;
import com.restaurant.munchies.services.OrdersService;
import com.restaurant.munchies.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class GroupOrderServiceImpl implements GroupOrderService {

    @Autowired
    private GroupOrderRepository groupOrderRepository;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private MailService mailService;



    @Override
    public GroupOrder saveOrUpdate(GroupOrder groupOrder) {
        return groupOrderRepository.saveAndFlush(groupOrder);
    }

    @Override
    public void delete(GroupOrder groupOrder) {
        groupOrderRepository.delete(groupOrder);
    }

    @Override
    public List<GroupOrder> listAllGroupOrder() {
        List<GroupOrder>listGroupOrder = groupOrderRepository.findAll();
        return listGroupOrder;
    }

    @Override
    public GroupOrder findOne(UUID id) {
        GroupOrder groupOrder = groupOrderRepository.getOne(id);
        return  groupOrder;
    }

//    @Override
//    public GroupOrder findByUUID(UUID uuid) {
//        return groupOrderRepository.findByUUID(uuid);
//
//    }

    @Override
    public GroupOrder findById(UUID id) {
        return  groupOrderRepository.findById(id).get();
    }

    @Override
    public List<GroupOrder> listActiveGroupOrder(UUID id) {
        Restaurant r = restaurantService.findById(id);
        List<GroupOrder> listOrders = r.getGroupOrderList();
        List<GroupOrder> listActiveOrders = new ArrayList<>();
        for(GroupOrder order : listOrders){
            long countDownDate = order.getCreated().getTime() + 60000 * order.getTimeout();
            long now = System.currentTimeMillis();
            long distance = countDownDate - now;
            if(distance>0){
                listActiveOrders.add(order);
            }
        }

        return listActiveOrders;
    }

    @Override
    public void setEmailTimer(long delay, GroupOrder groupOrder) {
        delay = groupOrder.getTimeout() * 60 *1000 + 5000;
        mailService.emailTimer(delay, groupOrder);
    }
}
