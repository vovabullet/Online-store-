package com.example.AppStoreSpring.repository;

import com.example.AppStoreSpring.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.isDeleted = false")
    List<Product> findAllByIsDeletedFalse();
}
