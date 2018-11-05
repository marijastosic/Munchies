package com.restaurant.munchies.services;

import com.restaurant.munchies.dto.GroupOrderDTO;
import com.restaurant.munchies.dto.RestaurantDTO;
import com.restaurant.munchies.entities.GroupOrder;
import com.restaurant.munchies.entities.Restaurant;
import com.restaurant.munchies.entities.User;

import javax.mail.MessagingException;

public interface MailService {
    public void sendEmailAboutOrders(GroupOrder groupOrder) throws MessagingException;
    public void emailTimer(long delay, GroupOrder groupOrder);
}
