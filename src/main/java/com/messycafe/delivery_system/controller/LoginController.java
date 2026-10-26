package com.messycafe.delivery_system.controller;

import com.messycafe.delivery_system.dto.LoginRequest;
import com.messycafe.delivery_system.model.User;
import com.messycafe.delivery_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allows your HTML files to talk to this Java app
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Helpful for debugging: check your IDE console when you click login
        System.out.println("Login attempt for user: " + loginRequest.getUsername());

        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        if (user.isPresent()) {
            User foundUser = user.get();
            
            // Compare the password from the request with the password in the DB
            if (foundUser.getPassword().equals(loginRequest.getPassword())) {
                System.out.println("Login successful for: " + foundUser.getDisplay_name());
                return ResponseEntity.ok(foundUser);
            }
        }

        System.out.println("Login failed for: " + loginRequest.getUsername());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
}