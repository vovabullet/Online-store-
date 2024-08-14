package com.example.AppStoreSpring.repository;

import com.example.AppStoreSpring.model.Order;
import com.example.AppStoreSpring.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems", "orderItems.product"})
    List<Order> findByUser(User user);
    @NonNull
    @EntityGraph(attributePaths = {"orderItems", "orderItems.product"})
    List<Order> findAll();
}
