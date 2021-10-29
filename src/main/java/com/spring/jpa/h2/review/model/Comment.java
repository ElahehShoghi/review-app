package com.spring.jpa.h2.review.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.jpa.h2.review.constant.AcceptanceStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String text;
    private Date createDate;
    @ManyToOne
    @JsonIgnore
    private Product commentedFor;
    @ManyToOne
    @JsonIgnore
    private User commentedBy;
    private AcceptanceStatus status;
}
