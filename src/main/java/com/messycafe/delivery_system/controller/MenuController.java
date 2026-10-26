package com.messycafe.delivery_system.controller;

import com.messycafe.delivery_system.model.MenuItem;
import com.messycafe.delivery_system.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*") 
public class MenuController {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @GetMapping
    public List<MenuItem> getAllItems() {
        return menuItemRepository.findAll();
    }

    @PostMapping("/add")
    public MenuItem addMenuItem(@RequestBody MenuItem item) {
        return menuItemRepository.save(item);
    }

    // Changed Integer to Long to match your new Repository fix
    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable Long id) {
        menuItemRepository.deleteById(id);
    }

    // Changed Integer to Long here as well
    @PutMapping("/{id}")
    public MenuItem updateMenuItem(@PathVariable Long id, @RequestBody MenuItem newItemDetails) {
        return menuItemRepository.findById(id)
                .map(item -> {
                    item.setName(newItemDetails.getName());
                    item.setCategory(newItemDetails.getCategory());
                    item.setPrice(newItemDetails.getPrice());
                    item.setStockQty(newItemDetails.getStockQty());
                    item.setImageUrl(newItemDetails.getImageUrl());
                    return menuItemRepository.save(item);
                })
                .orElseGet(() -> {
                    // Ensures the ID is set correctly if it creates a new one
                    return menuItemRepository.save(newItemDetails);
                });
    }
}