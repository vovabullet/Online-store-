package com.example.AppStoreSpring.controller;

import com.example.AppStoreSpring.model.CartItem;
import com.example.AppStoreSpring.model.Order;
import com.example.AppStoreSpring.model.User;
import com.example.AppStoreSpring.service.CartService;
import com.example.AppStoreSpring.service.OrderService;
import com.example.AppStoreSpring.service.ProductService;
import com.example.AppStoreSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/products")
    public String viewProductsPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "user/products";
    }

    @GetMapping("/cart")
    public String userCart(Model model, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        List<CartItem> cartItems = cartService.getCartItems(currentUser);
        model.addAttribute("cartItems", cartItems);
        return "user/cart";
    }

    @PostMapping("/placeOrder")
    public String placeOrder(@RequestParam String deliveryAddress, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        orderService.placeOrder(currentUser, deliveryAddress);
        return "redirect:/user/profile";
    }

    @GetMapping("/profile")
    public String userProfile(Model model, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        List<Order> orders = orderService.getOrdersByUser(currentUser);
        model.addAttribute("user", currentUser);
        model.addAttribute("orders", orders);
        return "user/profile";
    }

    @PostMapping("/addToCart/{productId}")
    public String addToCart(@PathVariable Long productId, Principal principal,
                            @RequestHeader(value = "referer", required = false) String referer) {
        User currentUser = userService.findByUsername(principal.getName());
        cartService.addProductToCart(currentUser, productId);
        return "redirect:" + (referer != null ? referer : "/user/products");
    }

    @GetMapping("/removeFromCart/{productId}")
    public String removeFromCart(@PathVariable Long productId, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        cartService.removeProductFromCart(currentUser, productId);
        return "redirect:/user/cart";
    }
}
