package com.restaurant.munchies.services;

import com.restaurant.munchies.entities.GroupOrder;
import com.restaurant.munchies.entities.Orders;
import com.restaurant.munchies.entities.User;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;

import java.util.List;

public interface OrdersService {
    public void saveOrUpdate(Orders orders);
    public void delete(Orders orders);
    List<Orders> listAllOrders();
    public Orders findOne(Integer id);
    public void save(Orders orders);
    void flush(Orders orders);
    double getSumAllOrdersByGroupOrder(GroupOrder groupOrder);
    List<Orders> getAllOrdersForGroupOrder(GroupOrder groupOrder);
}
