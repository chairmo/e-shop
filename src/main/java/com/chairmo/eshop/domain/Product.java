package com.chairmo.eshop.domain;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private Integer stock;

    @Column
    private String imageUrl;
    
    @Column
    private Float cost;

    @Column
    private Integer sold;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_details_id", nullable = false)
//    private OrderDetails orderDetails;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime date;

    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        date = OffsetDateTime.now();
        lastUpdated = date;
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdated = OffsetDateTime.now();
    }

}

