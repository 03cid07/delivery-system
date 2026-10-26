package com.messycafe.delivery_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id") // FIX: Matches your MySQL primary key 'order_id'
    private Long orderId;

    @Column(name = "user_id") 
    private Long userId; 

    private String customerName; // Matches 'customer_name' (JPA handles camelCase to underscore automatically)
    
    @Column(name = "delivery_address") // Explicitly mapping for safety
    private String deliveryAddress;

    @Column(name = "rider_id")
    private Long riderId; 
    
    @Column(name = "rider_name")
    private String riderName; 

    @Column(name = "total_amount")
    private Double totalAmount;
    
    @Column(name = "order_date", insertable = false, updatable = false)
    private LocalDateTime orderDate;

    private String status = "PENDING";

    // Standard relationship for items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();

    @Column(name = "contact_number")
    private String contactNumber;

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public Order() {}

    // Getters and Setters
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public Long getRiderId() { return riderId; }
    public void setRiderId(Long riderId) { this.riderId = riderId; }

    public String getRiderName() { return riderName; }
    public void setRiderName(String riderName) { this.riderName = riderName; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    
}