package com.messycafe.delivery_system.repository;

import com.messycafe.delivery_system.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByOrderIdOrderBySentAtAsc(Long orderId);
}