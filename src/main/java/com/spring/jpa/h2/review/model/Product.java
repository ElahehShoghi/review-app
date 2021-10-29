package com.spring.jpa.h2.review.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @ManyToOne
    private Provider provider;
    @OneToMany
    private List<Comment> comments;
    @OneToMany
    private List<Vote> votes;

    public Product(String name) {
        this.name = name;
    }
}
