package com.restaurant.munchies.controller;

import com.restaurant.munchies.dto.RestaurantDTO;
import com.restaurant.munchies.dto.UserDTO;
import com.restaurant.munchies.entities.Restaurant;
import com.restaurant.munchies.entities.User;
import com.restaurant.munchies.services.RestaurantService;
import com.restaurant.munchies.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    private RestaurantDTO convertToDto(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = modelMapper.map(restaurant, RestaurantDTO.class);
        return restaurantDTO;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login1");
        return modelAndView;
    }

    @GetMapping({"/", "/employee/list", "/employee"})
    public String listRestaurants(Model model) {
        List<Restaurant> theRestaurants = restaurantService.listAllRestaurant();

        List<RestaurantDTO> restaurantsDTO = theRestaurants.stream()
                .map(restaurant -> convertToDto(restaurant))
                .collect(Collectors.toList());

        model.addAttribute("restaurants", restaurantsDTO);
        return "employee-list-restaurants";
    }


    @GetMapping("/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", modelMapper.map(user, UserDTO.class));
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView createNewUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("user", userDTO);
            modelAndView.setViewName("registration");
        } else {
            if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
                modelAndView.addObject("errorMessage", "Password doesn't matches confirmation password!");
                modelAndView.addObject("user", userDTO);
                modelAndView.setViewName("registration");
                return modelAndView;
            }

            User userExists = userService.findUserByEmail(userDTO.getEmail());
            if (userExists != null) {
                modelAndView.addObject("errorMessage", "User with given email already exists!");
                modelAndView.addObject("user", userDTO);
                modelAndView.setViewName("registration");
                return modelAndView;
            }
            User user = modelMapper.map(userDTO, User.class);
            user = userService.saveUser(user);
            modelAndView.addObject("successMessage", "Successful registration! Please login!");
            modelAndView.setViewName("login1");

        }
        return modelAndView;
    }
}
