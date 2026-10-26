package com.messycafe.delivery_system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String username;
    private String password;
    private String role;
    private String display_name;
    private String address;
    private String phone_number;
    
    // ADD THIS LINE: This matches the 'status' column in your MySQL table
    private String status;

    // 1. Mandatory No-Args Constructor for JPA
    public User() {}

    // 2. Optional All-Args Constructor
    public User(String username, String password, String role, String display_name, String status) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.display_name = display_name;
        this.status = status;
    }

    // --- GETTERS AND SETTERS ---

    public Long getUser_id() { return user_id; }
    public void setUser_id(Long user_id) { this.user_id = user_id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDisplay_name() { return display_name; }
    public void setDisplay_name(String display_name) { this.display_name = display_name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone_number() { return phone_number; }
    public void setPhone_number(String phone_number) { this.phone_number = phone_number; }

    // ADD THESE METHODS: Getter and Setter for status
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}