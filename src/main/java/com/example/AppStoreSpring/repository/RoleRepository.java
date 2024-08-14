package com.example.AppStoreSpring.repository;

import com.example.AppStoreSpring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
