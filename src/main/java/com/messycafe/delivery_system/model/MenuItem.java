package com.messycafe.delivery_system.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "menu_items")
@Data
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    private String name;
    private String category;
    private Double price;
    private Integer stockQty;

    // We change this to LONGTEXT so it can store the full image data
    @Column(columnDefinition = "LONGTEXT")
    private String imageUrl;
}