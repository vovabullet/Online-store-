package com.example.AppStoreSpring.service;

import com.example.AppStoreSpring.model.Product;
import com.example.AppStoreSpring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAllByIsDeletedFalse();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProductById(Long id) {
        Product product = getProductById(id);
        if (product != null) {
            product.setDeleted(true);
            productRepository.save(product);
        }
    }
}
