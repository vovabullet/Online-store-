package com.example.AppStoreSpring.controller;

import com.example.AppStoreSpring.model.Order;
import com.example.AppStoreSpring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/storekeeper")
public class StorekeeperController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String viewOrdersPage(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "storekeeper/orders";
    }

    @PostMapping("/updateOrderStatus/{orderId}")
    public String updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return "redirect:/storekeeper/orders";
    }
}
