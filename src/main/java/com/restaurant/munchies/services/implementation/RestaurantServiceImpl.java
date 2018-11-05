package com.restaurant.munchies.services.implementation;

import com.restaurant.munchies.entities.Orders;
import com.restaurant.munchies.entities.Restaurant;
import com.restaurant.munchies.repository.OrdersRepository;
import com.restaurant.munchies.repository.RestaurantRepository;
import com.restaurant.munchies.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Override
    public void saveOrUpdate(Restaurant restaurant) {
        restaurantRepository.saveAndFlush(restaurant);
    }

    @Override
    public void delete(UUID id) {
     restaurantRepository.deleteById(id);
    }

    @Override
    public List<Restaurant> listAllRestaurant() {
        List<Restaurant> listRestaurants = restaurantRepository.findAll();
        return listRestaurants;
    }

    @Override
    public Restaurant findOne(UUID id) {
        Restaurant restaurant = restaurantRepository.getOne(id);
        return restaurant;
    }

    @Override
    public Restaurant findById(UUID id) {
        return restaurantRepository.findById(id).get();
    }
}
