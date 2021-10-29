package com.spring.jpa.h2.review.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userName;
    @OneToMany
    private List<Comment> comments;
    @OneToMany
    private List<Vote> votes;
}
