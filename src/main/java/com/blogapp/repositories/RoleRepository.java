package com.blogapp.repositories;

import com.blogapp.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Integer> {
    // Additional query methods can be defined here if needed
}
