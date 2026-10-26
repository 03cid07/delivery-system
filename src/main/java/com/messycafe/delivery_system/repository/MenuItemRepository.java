package com.messycafe.delivery_system.repository;

import com.messycafe.delivery_system.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository; // Using JpaRepository is good!
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    // Changing 'Integer' to 'Long' fixes the mismatch with your Controller
}