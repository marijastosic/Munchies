package com.restaurant.munchies.services;

import com.restaurant.munchies.entities.Orders;
import com.restaurant.munchies.entities.Restaurant;
import com.restaurant.munchies.entities.User;

import java.util.List;
import java.util.UUID;

public interface RestaurantService {

    public void saveOrUpdate(Restaurant restaurant);
    public void delete(UUID id);
    List<Restaurant>listAllRestaurant();
    public Restaurant findOne(UUID id);
    Restaurant findById(UUID id);


}
