package com.example.AppStoreSpring.service;

import com.example.AppStoreSpring.model.CartItem;
import com.example.AppStoreSpring.model.Product;
import com.example.AppStoreSpring.model.User;
import com.example.AppStoreSpring.repository.CartItemRepository;
import com.example.AppStoreSpring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUserOrderByAddedDate(user);
    }

    public void addProductToCart(User user, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.getInStock() > 0) {
            CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product);
            if (cartItem != null) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                System.out.println("Updated quantity for product: " + product.getName() + " to " + cartItem.getQuantity());
            } else {
                cartItem = new CartItem();
                cartItem.setUser(user);
                cartItem.setProduct(product);
                cartItem.setQuantity(1);
                System.out.println("Added new product to cart: " + product.getName());
            }
            product.setInStock(product.getInStock() - 1);
            productRepository.save(product);
            cartItemRepository.save(cartItem);
        } else {
            throw new RuntimeException("Product is out of stock");
        }
    }

    public void removeProductFromCart(User user, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (cartItem != null) {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                System.out.println("Updated quantity for product: " + product.getName() + " to " + cartItem.getQuantity());
                cartItemRepository.save(cartItem);
            } else {
                cartItemRepository.delete(cartItem);
            }
            product.setInStock(product.getInStock() + 1);
            productRepository.save(product);
        }
    }
}
