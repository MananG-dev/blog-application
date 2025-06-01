package com.blogapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "Roles")
@Table(name = "roles")
@Data
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer roleId;
    private String roleName;
}
