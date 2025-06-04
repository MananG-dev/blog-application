package com.blogapp;

import com.blogapp.entities.Roles;
import com.blogapp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

import static com.blogapp.utils.AppConstants.*;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Initializing default roles...");

		try {
			if (roleRepository.count() == 0) {
				Roles adminRole = Roles.builder().roleId(ADMIN_USER).roleName(ADMIN_USER_ROLE).build();
				Roles normalRole = Roles.builder().roleId(NORMAL_USER).roleName(NORMAL_USER_ROLE).build();

				List<Roles> roles = List.of(adminRole, normalRole);
				roleRepository.saveAll(roles);
				roles.forEach(role -> System.out.println("Initialized role: " + role.getRoleName()));
			} else {
				System.out.println("Roles already exist, skipping initialization.");
			}
		} catch (Exception e) {
			System.out.println("Error initializing roles: " + e.getMessage());
		}
	}
}