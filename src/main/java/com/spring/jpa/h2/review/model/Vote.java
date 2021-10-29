package com.spring.jpa.h2.review.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.jpa.h2.review.constant.AcceptanceStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@Table(name = "votes")
@Data
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Max(10)
    @Min(0)
    private long score;
    private Date createDate;
    @ManyToOne
    @JsonIgnore
    private Product votedFor;
    @ManyToOne
    @JsonIgnore
    private User votedBy;
    private AcceptanceStatus status;
}
