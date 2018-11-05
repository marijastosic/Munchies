package com.restaurant.munchies.controller;

import com.restaurant.munchies.dto.GroupOrderDTO;
import com.restaurant.munchies.dto.OrdersDTO;
import com.restaurant.munchies.dto.RestaurantDTO;
import com.restaurant.munchies.entities.GroupOrder;
import com.restaurant.munchies.entities.Orders;
import com.restaurant.munchies.entities.Restaurant;
import com.restaurant.munchies.services.GroupOrderService;
import com.restaurant.munchies.services.MailService;
import com.restaurant.munchies.services.OrdersService;
import com.restaurant.munchies.services.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private GroupOrderService groupOrderService;
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MailService mailService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/list/{id}")
    public ModelAndView listRestaurantById(@PathVariable("id") String id) {
        ModelAndView model = new ModelAndView();
        Restaurant r = restaurantService.findOne(UUID.fromString(id));
        model.addObject("restaurant", modelMapper.map(r, RestaurantDTO.class));
        model.setViewName("employee-list-restaurant");
        return model;
    }

    @GetMapping("/showFormForAddByRestaurantId/{id}")
    public String showFormForAddByRestaurantId(Model model, @PathVariable("id") String id) {
        GroupOrderDTO groupOrderDto = new GroupOrderDTO();
        groupOrderDto.setRestaurantIdString(id);
        model.addAttribute("groupOrder", groupOrderDto);
        return "new-group-order-form";
    }

    @PostMapping("/saveGroupOrder")
    public String saveGroupOrder(@Valid @ModelAttribute("groupOrder") GroupOrderDTO groupOrder, BindingResult theBindingResult, Model model) {
        if (theBindingResult.hasErrors()) {
            model.addAttribute("groupOrder", groupOrder);
            return "new-group-order-form";
        } else {
            Restaurant restaurant = restaurantService.findOne(UUID.fromString(groupOrder.getRestaurantIdString()));
            groupOrder.setRestaurantId(modelMapper.map(restaurant, RestaurantDTO.class));
            groupOrder.setCreated(new Date());
            long timestamp = groupOrder.getCreated().getTime() + 60000 * groupOrder.getTimeout();
            groupOrder = modelMapper.map(groupOrderService.saveOrUpdate(modelMapper.map(groupOrder, GroupOrder.class)), GroupOrderDTO.class);


            long timestampForMail = groupOrder.getTimeout() * 60 * 1000 + 5000;
            mailService.emailTimer(timestampForMail, modelMapper.map(groupOrder, GroupOrder.class));

            OrdersDTO ordersDto = new OrdersDTO();
            ordersDto.setGroupOrderIdString(groupOrder.getId().toString());
            model.addAttribute("groupOrder", modelMapper.map(groupOrder, GroupOrderDTO.class));
            model.addAttribute("orders", ordersDto);
            return "group-order";
        }
    }

    @PostMapping("/saveOrders")
    public String saveOrders(@Valid @ModelAttribute("orders") OrdersDTO orders, BindingResult theBindingResult, Model model) {
        if (theBindingResult.hasErrors()) {
            GroupOrder groupOrder = groupOrderService.findOne(UUID.fromString(orders.getGroupOrderIdString()));
            orders.setGroupOrderId(modelMapper.map(groupOrder, GroupOrderDTO.class));
            model.addAttribute("orders", orders);
            model.addAttribute("groupOrder", modelMapper.map(groupOrder, GroupOrderDTO.class));
            return "group-order";
        } else {
            GroupOrder groupOrder = groupOrderService.findOne(UUID.fromString(orders.getGroupOrderIdString()));
            orders.setGroupOrderId(modelMapper.map(groupOrder, GroupOrderDTO.class));

            ordersService.save(modelMapper.map(orders, Orders.class));
            model.addAttribute("groupOrder", modelMapper.map(groupOrder, GroupOrderDTO.class));
            double total = ordersService.getSumAllOrdersByGroupOrder(groupOrder);
            model.addAttribute("total", total);
            return "group-order";
        }
    }

    @GetMapping("/groupOrder/{id}")
    public String getGroupOrderById(@PathVariable("id") String id, Model model) {
        GroupOrder groupOrder = groupOrderService.findOne(UUID.fromString(id));

        model.addAttribute("groupOrder", modelMapper.map(groupOrder, GroupOrderDTO.class));
        OrdersDTO ordersDto = new OrdersDTO();
        ordersDto.setGroupOrderIdString(groupOrder.getId().toString());

        double total = ordersService.getSumAllOrdersByGroupOrder(groupOrder);
        model.addAttribute("total", total);
        model.addAttribute("orders", ordersDto);
        return "group-order";
    }

    //@GetMapping("/sendEmail/{id}")
   // public String sendEmail(@PathVariable("id") String id, Model model) throws MessagingException{
    //    GroupOrder groupOrder = groupOrderService.findOne(UUID.fromString(id));

     //   List<Orders> listOrders = groupOrder.getOrdersList();
     //   if(!listOrders.isEmpty()){
     //       mailService.sendEmailAboutOrders(groupOrder);
     //   }
     //   else{
     //       model.addAttribute("errorMessage", "Error with sent order, please check all information!");
     //   }
//        if (groupOrder.getOrdersList() != null) {
//            mailService.sendEmailAboutOrders(groupOrder);
//        } else {
//            model.addAttribute("errorMessage", "Error with sent order, please check all information!");
//        }
        //mailService.sendEmailAboutOrders(groupOrder);

     //   return "redirect:/";
  //  }

    @GetMapping("/sendEmail/{id}")
    public ModelAndView sendEmail(@PathVariable("id") String id, Model model) throws MessagingException{

        ModelAndView modelAndView = new ModelAndView();
        GroupOrder groupOrder = groupOrderService.findOne(UUID.fromString(id));

        List<Orders> listOrders = groupOrder.getOrdersList();
        if(!listOrders.isEmpty()){
            mailService.sendEmailAboutOrders(groupOrder);
        }
        else{
            modelAndView.addObject("errorMessage", "Error with sent order, please check all information!");
            modelAndView.setViewName("employee-list-restaurants");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/");
       return modelAndView;
    }
}
