package com.example.AppStoreSpring.service;

import com.example.AppStoreSpring.model.*;
import com.example.AppStoreSpring.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public void placeOrder(User user, String deliveryAddress) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setDeliveryAddress(deliveryAddress);
        order.setStatus("сборка");
        order.setOrderDate(new Date());
        orderRepository.save(order);

        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);
            System.out.println("Added product to order: " + cartItem.getProduct().getName() + " x " + cartItem.getQuantity());
        }

        orderItemRepository.saveAll(orderItems);
        order.setOrderItems(orderItems);
        cartItemRepository.deleteAll(cartItems);

        System.out.println("Order ID: " + order.getId());
        for (OrderItem item : order.getOrderItems()) {
            System.out.println("Item: " + item.getProduct().getName() + " x " + item.getQuantity());
        }
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
