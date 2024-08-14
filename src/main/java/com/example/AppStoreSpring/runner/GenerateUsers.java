package com.example.AppStoreSpring.runner;

import com.example.AppStoreSpring.model.Role;
import com.example.AppStoreSpring.model.User;
import com.example.AppStoreSpring.repository.RoleRepository;
import com.example.AppStoreSpring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class GenerateUsers implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (roleRepository.findByName("ROLE_USER") == null) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByName("ROLE_STOREKEEPER") == null) {
            Role storekeeperRole = new Role();
            storekeeperRole.setName("ROLE_STOREKEEPER");
            roleRepository.save(storekeeperRole);
        }

        if (userRepository.findByUsername("user") == null) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(new HashSet<>(Set.of(roleRepository.findByName("ROLE_USER"))));
            userRepository.save(user);
        }

        if (userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRoles(new HashSet<>(Set.of(roleRepository.findByName("ROLE_ADMIN"))));
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("storekeeper") == null) {
            User storekeeper = new User();
            storekeeper.setUsername("storekeeper");
            storekeeper.setPassword(passwordEncoder.encode("password"));
            storekeeper.setRoles(new HashSet<>(Set.of(roleRepository.findByName("ROLE_STOREKEEPER"))));
            userRepository.save(storekeeper);
        }
    }
}
