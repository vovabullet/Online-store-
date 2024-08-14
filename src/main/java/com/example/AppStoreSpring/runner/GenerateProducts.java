package com.example.AppStoreSpring.runner;

import com.example.AppStoreSpring.repository.ProductRepository;
import com.example.AppStoreSpring.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class GenerateProducts implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) {

        Product product1 = new Product();
        product1.setName("Ожерелье из жемчуга");
        product1.setCategory("Ювелирные изделия");
        product1.setBrand("Tiffany & Co");
        product1.setPrice(399.0);
        product1.setInStock(32);

        Product product2 = new Product();
        product2.setName("Bluetooth наушники");
        product2.setCategory("Электроника");
        product2.setBrand("Sony");
        product2.setPrice(169.9);
        product2.setInStock(15);

        Product product3 = new Product();
        product3.setName("Беспроводная клавиатура с подсветкой");
        product3.setCategory("Электроника");
        product3.setBrand("Logitech");
        product3.setPrice(334.9);
        product3.setInStock(3);

        Product product4 = new Product();
        product4.setName("Кроссовки");
        product4.setCategory("Обувь");
        product4.setBrand("Nike");
        product4.setPrice(229.0);
        product4.setInStock(2);

        productRepository.saveAll(Arrays.asList(product1, product2, product3, product4));
    }
}
