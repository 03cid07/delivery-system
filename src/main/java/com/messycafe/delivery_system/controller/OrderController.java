package com.messycafe.delivery_system.controller;

import com.messycafe.delivery_system.model.Order;
import com.messycafe.delivery_system.model.OrderItem;
import com.messycafe.delivery_system.model.MenuItem;
import com.messycafe.delivery_system.repository.OrderRepository;
import com.messycafe.delivery_system.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.messycafe.delivery_system.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*") 


public class OrderController {
    

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserRepository userRepository;

    // --- NEW: FETCH ORDERS FOR A SPECIFIC USER (MARK) ---
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId) {
        // This uses the custom method we added to your Repository
        List<Order> orders = orderRepository.findByUserIdOrderByOrderDateDesc(userId);
        return ResponseEntity.ok(orders);
    }
    

    @PostMapping
    @Transactional 
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        System.out.println("Placing order for User ID: " + order.getUserId());
        
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                Optional<MenuItem> menuData = menuItemRepository.findById(item.getItemId());
                if (menuData.isPresent()) {
                    MenuItem menuItem = menuData.get();
                    
                    item.setItemName(menuItem.getName());  // ← ADD THIS
                    item.setPrice(menuItem.getPrice());    // ← ADD THIS

                    int currentStock = menuItem.getStockQty();
                    int orderedQty = item.getQuantity();
                    int newStock = Math.max(0, currentStock - orderedQty);
                    menuItem.setStockQty(newStock);
                    menuItemRepository.save(menuItem);
                }
                item.setOrder(order); 
            }
        }

        

        if (order.getUserId() != null) {
            userRepository.findById(order.getUserId())
                .ifPresent(user -> order.setContactNumber(user.getPhone_number()));
        }
        
        Order savedOrder = orderRepository.save(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
        
    }

    // ACCEPT ORDER: Admin assigns rider and sets status to READY
    @PutMapping("/{id}/accept")
    public ResponseEntity<Order> acceptOrder(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Optional<Order> orderData = orderRepository.findById(id);
        if (orderData.isPresent()) {
            Order order = orderData.get();
            order.setStatus("READY");
            order.setRiderName(body.get("riderName"));
            order.setRiderId(Long.parseLong(body.get("riderId")));
            return ResponseEntity.ok(orderRepository.save(order));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody String newStatus) {
        Optional<Order> orderData = orderRepository.findById(id);

        if (orderData.isPresent()) {
            Order order = orderData.get();
            String cleanStatus = newStatus.replace("\"", "");
            order.setStatus(cleanStatus);
            return ResponseEntity.ok(orderRepository.save(order));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
}