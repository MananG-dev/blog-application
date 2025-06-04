package com.blogapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "Roles")
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Roles {
    @Id
    private Integer roleId;
    private String roleName;
}
