package com.restaurant.munchies.controller;

import com.restaurant.munchies.dto.GroupOrderDTO;
import com.restaurant.munchies.dto.RestaurantDTO;
import com.restaurant.munchies.entities.GroupOrder;
import com.restaurant.munchies.entities.Orders;
import com.restaurant.munchies.entities.Restaurant;
import com.restaurant.munchies.services.GroupOrderService;
import com.restaurant.munchies.services.MailService;
import com.restaurant.munchies.services.OrdersService;
import com.restaurant.munchies.services.RestaurantService;
import com.restaurant.munchies.storage.FileSystemStorageService;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Value("${url.path}")
    private String urlPath;

    private Path path;

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private GroupOrderService groupOrderService;
    @Autowired
    private FileSystemStorageService storageService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MailService mailService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    private RestaurantDTO convertToDto(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = modelMapper.map(restaurant, RestaurantDTO.class);
        return restaurantDTO;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/list")
    public String listRestaurants(Model model) {
        List<Restaurant> theRestaurants = restaurantService.listAllRestaurant();

        List<RestaurantDTO> restaurantsDTO = theRestaurants.stream()
                .map(restaurant -> convertToDto(restaurant))
                .collect(Collectors.toList());

        model.addAttribute("restaurants", restaurantsDTO);
        return "list-restaurants";
    }

    @GetMapping("/list/{id}")
    public ModelAndView listRestaurantById(@PathVariable("id") String id) {
        ModelAndView model = new ModelAndView();
        Restaurant r = restaurantService.findOne(UUID.fromString(id));
        model.addObject("restaurant", modelMapper.map(r, RestaurantDTO.class));
        model.setViewName("list-restaurant");
        return model;
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Restaurant theRestaurant = new Restaurant();
        model.addAttribute("restaurant", modelMapper.map(theRestaurant, RestaurantDTO.class));
        return "restaurant-form";
    }

    @PostMapping("/saveRestaurant")
    public String saveRestaurant(@Valid @ModelAttribute("restaurant") RestaurantDTO restaurantDto, BindingResult theBindingResult, Model model, HttpServletRequest request) {

        if (theBindingResult.hasErrors()) {
            model.addAttribute("restaurant", restaurantDto);
            return "restaurant-form";
        } else {
            restaurantDto.setEmail(restaurantDto.getEmail());
            restaurantDto.setMenuUrl(urlPath + restaurantDto.getUploadFile().getOriginalFilename());
            restaurantService.saveOrUpdate(modelMapper.map(restaurantDto, Restaurant.class));

            storageService.store(restaurantDto.getUploadFile());
//            MultipartFile uploadFile = restaurantDto.getUploadFile();
//            String rootDirectory = request.getSession().getServletContext().getRealPath("/");
//            path = Paths.get(rootDirectory + "C:\\Users\\UNDP\\Documents\\IntelliJ IDEA\\munchies\\uploads\\");
//
//            if (uploadFile != null && !uploadFile.isEmpty()) {
//                try {
//                    uploadFile.transferTo(new File(path.toString()));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    throw new RuntimeException("Product image saving failed.", e);
//                }
//            }
            return "redirect:/admin/list";
        }
//        return "redirect:/admin/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("restaurantId") String id, Model model) {
        Restaurant restaurant = restaurantService.findOne(UUID.fromString(id));
        model.addAttribute("restaurant", modelMapper.map(restaurant, RestaurantDTO.class));
        return "restaurant-form";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormUpdate(@PathVariable("id") String id, Model model) {
        Restaurant restaurant = restaurantService.findOne(UUID.fromString(id));
        model.addAttribute("restaurant", modelMapper.map(restaurant, RestaurantDTO.class));
        return "restaurant-form";
    }

    @GetMapping("/delete")
    public String deleteRestaurant(@RequestParam("restaurantId") String id) {
        restaurantService.delete(UUID.fromString(id));
        return "redirect:/admin/list";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteRestaurantById(@PathVariable("id") String id) {
        restaurantService.delete(UUID.fromString(id));

        return new ModelAndView("redirect:/admin/list");
    }

    @GetMapping("/listOrders/{id}")
    public ModelAndView listOrdersById(@PathVariable("id") String id) {
        ModelAndView model = new ModelAndView();
        //Restaurant r = restaurantService.findById(id);
        List<GroupOrder> listActiveGroupOrder = groupOrderService.listActiveGroupOrder(UUID.fromString(id));
        //List<GroupOrder> listGroupOrders = r.getGroupOrderList();
        GroupOrder groupOrder = new GroupOrder();
        groupOrder.setOrdersList(ordersService.listAllOrders());
        model.addObject("groupOrder", modelMapper.map(groupOrder, GroupOrderDTO.class));
        //model.addObject("listGroupOrders", listGroupOrders);
        List<GroupOrderDTO> groupOrdersDTO = listActiveGroupOrder.stream()
                .map(groupOrder1 -> convertToDto(groupOrder1))
                .collect(Collectors.toList());
        model.addObject("listActiveGroupOrder", groupOrdersDTO);
        double total = ordersService.getSumAllOrdersByGroupOrder(groupOrder);
        model.addObject("total", total);
        model.setViewName("list-orders");
        return model;
    }

    private GroupOrderDTO convertToDto(GroupOrder groupOrder) {
        GroupOrderDTO restaurantDTO = modelMapper.map(groupOrder, GroupOrderDTO.class);
        return restaurantDTO;
    }
}
