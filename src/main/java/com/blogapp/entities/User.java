package com.blogapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="user_name", nullable=false, length=25)
    private String name;
    // @Column(name="user_email", nullable = false)
    private String email;
    // @Column(name="user_password", nullable = false)
    private String password;
    // @Column(name="user_description", nullable = true)
    private String about;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> post;

}