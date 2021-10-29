package com.spring.jpa.h2.review.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "providers")
@Data
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String address;
    private String tel;
    private String postCode;
    @OneToMany
    @JsonBackReference
    private List<Product> products;
}
