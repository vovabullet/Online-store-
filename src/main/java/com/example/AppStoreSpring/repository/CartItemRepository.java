package com.example.AppStoreSpring.repository;

import com.example.AppStoreSpring.model.CartItem;
import com.example.AppStoreSpring.model.Product;
import com.example.AppStoreSpring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    CartItem findByUserAndProduct(User user, Product product);

    @Query("SELECT c FROM CartItem c WHERE c.user = :user ORDER BY c.id ASC")
    List<CartItem> findByUserOrderByAddedDate(@Param("user") User user);
}
