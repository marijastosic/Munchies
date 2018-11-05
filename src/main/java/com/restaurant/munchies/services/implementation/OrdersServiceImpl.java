package com.restaurant.munchies.services.implementation;

import com.restaurant.munchies.entities.GroupOrder;
import com.restaurant.munchies.entities.Orders;
import com.restaurant.munchies.entities.Restaurant;
import com.restaurant.munchies.repository.OrdersRepository;
import com.restaurant.munchies.repository.RestaurantRepository;
import com.restaurant.munchies.services.OrdersService;
import com.restaurant.munchies.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;


    @Override
    public void saveOrUpdate(Orders orders) {
        ordersRepository.saveAndFlush(orders);
    }

    @Override
    public void delete(Orders orders) {
        ordersRepository.delete(orders);
    }

    @Override
    public List<Orders> listAllOrders() {
        List<Orders> listOrders = ordersRepository.findAll();
        return listOrders;
    }

    @Override
    public Orders findOne(Integer id) {
        Orders order = ordersRepository.getOne(id);
        return order;
    }

    @Override
    public void save(Orders orders) {
        ordersRepository.save(orders);
    }

    @Override
    public void flush(Orders orders) {
        ordersRepository.flush();
    }

//    @Override
//    public double getSumAllOrdersByGroupOrder(GroupOrder groupOrder) {
//        double total = 0;
//         List<Orders> ordersList = ordersRepository.findAll();
//
//         for (Orders o : ordersList){
//               if(o.getGroupOrderId().getId().toString().equals(groupOrder.getId().toString())){
//                   total += o.getPrice();
//               }
//
//         }
//        return total;
//    }

    @Override
    public double getSumAllOrdersByGroupOrder(GroupOrder groupOrder) {
        double total = 0;
        List<Orders> ordersList = ordersRepository.findAll();

        for (Orders o : ordersList){
            if(o.getGroupOrderId().getId()== (groupOrder.getId())){
                total += o.getPrice();
            }

        }
        return total;
    }
    @Override
    public List<Orders> getAllOrdersForGroupOrder(GroupOrder groupOrder) {
        List<Orders> ordersList = ordersRepository.findAll();

        List<Orders> listToRet = new ArrayList<>();

        for (Orders o : ordersList){
            if(o.getGroupOrderId().getId().toString().equals(groupOrder.getId().toString())){
                listToRet.add(o);
            }
        }
        return listToRet;
    }
}
