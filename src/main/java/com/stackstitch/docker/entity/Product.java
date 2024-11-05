package com.stackstitch.docker.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "Product")
public class Product {

    public Product(){

    }

    public Product(Long id, String name, String type, String price){
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private String price;


}
