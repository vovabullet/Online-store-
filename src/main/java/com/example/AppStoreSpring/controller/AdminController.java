package com.example.AppStoreSpring.controller;

import com.example.AppStoreSpring.model.Product;
import com.example.AppStoreSpring.model.Role;
import com.example.AppStoreSpring.model.User;
import com.example.AppStoreSpring.service.ProductService;
import com.example.AppStoreSpring.service.RoleService;
import com.example.AppStoreSpring.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/products")
    public String viewProductsPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    @GetMapping("/addProduct")
    public String showAddProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "admin/formProduct";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/editProduct/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/formProduct";
    }

    @PostMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product) {
        Product existingProduct = productService.getProductById(id);
        existingProduct.setName(product.getName());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setInStock(product.getInStock());
        productService.saveProduct(existingProduct);
        return "redirect:/admin/products";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/users")
    public String viewUsersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/users/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin/addUser";
    }

    @PostMapping("/users/add")
    public String addUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "admin/addUser";
        }
        if (userService.usernameExists(user.getUsername())) {
            result.rejectValue("username", "error.user", "Username is already taken");
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "admin/addUser";
        }
        Set<Role> roles = new HashSet<>();
        for (Long roleId : user.getRoles().stream().map(Role::getId).toList()) {
            roles.add(roleService.getRoleById(roleId));
        }
        user.setRoles(roles);
        userService.save(user);
        return "redirect:/admin/users";
    }
}
