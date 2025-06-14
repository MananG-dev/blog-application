package com.blogapp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="comments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    @Column(name="content")
    private String commentContent;

    @ManyToOne
    private Post post;
    @ManyToOne
    private User user;
}