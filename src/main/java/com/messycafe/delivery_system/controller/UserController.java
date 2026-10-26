    package com.messycafe.delivery_system.controller;

    import com.messycafe.delivery_system.model.User;
    import com.messycafe.delivery_system.repository.UserRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping("/api")
    @CrossOrigin(origins = "*") 
    public class UserController {

        @Autowired
        private UserRepository userRepository;

        // 1. GET ALL USERS: Used by the Customer Modal and Admin Panel
        @GetMapping("/users")
        public List<User> getAllUsers() {
            return userRepository.findAll();
        }

        // 2. REGISTER: Existing logic for new customers
        @PostMapping("/register")
        public ResponseEntity<?> registerUser(@RequestBody User newUser) {
            try {
                if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
                    return ResponseEntity.badRequest().body("Username is already taken!");
                }
                newUser.setRole("CUSTOMER");
                // Default status for new customers
                newUser.setAddress(""); 
                User savedUser = userRepository.save(newUser);
                return ResponseEntity.ok(savedUser);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
            }
        }

        // 4. CREATE RIDER: Admin adds a new rider account
@PostMapping("/users/riders")
public ResponseEntity<?> createRider(@RequestBody User newRider) {
    try {
        if (userRepository.findByUsername(newRider.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }
        newRider.setRole("RIDER");
        newRider.setStatus("Available");
        User saved = userRepository.save(newRider);
        return ResponseEntity.ok(saved);
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
    }
}

        // 3. UPDATE RIDER STATUS: The new "Admin Feature"
        // Use this to toggle a rider between 'Available' and 'Offline'
@PatchMapping("/users/{id}/status")
public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> statusUpdate) {
    return userRepository.findById(id).map(user -> {
        String newStatus = statusUpdate.get("status");
        
        if (!"RIDER".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.badRequest().body("Only Rider status can be managed here.");
        }

        // FIX: Change setAddress to setStatus
        user.setStatus(newStatus); 
        
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }).orElse(ResponseEntity.notFound().build());
}
    }

    