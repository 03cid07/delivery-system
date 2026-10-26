package com.messycafe.delivery_system.repository;

import com.messycafe.delivery_system.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
// 1. Switched to JpaRepository for better features (like sorting)
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * 2. This is the "Magic" method. 
     * Spring Data JPA will automatically generate the SQL: 
     * SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC;
     */
    List<Order> findByUserIdOrderByOrderDateDesc(Long userId);
}