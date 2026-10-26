package com.messycafe.delivery_system.controller;

import com.messycafe.delivery_system.model.SalesArchive;
import com.messycafe.delivery_system.repository.SalesArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "*")
public class SalesArchiveController {

    @Autowired
    private SalesArchiveRepository salesArchiveRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // Save today's report data
    @PostMapping("/archive")
    @Transactional
    public ResponseEntity<?> archiveAndReset(@RequestBody Map<String, String> body) {
        try {
            String reportData = body.get("reportData");
            String dateStr = body.get("date");
            LocalDate date = LocalDate.parse(dateStr);

            // Save archive
            SalesArchive archive = salesArchiveRepository
                .findByArchiveDate(date)
                .orElse(new SalesArchive());
            archive.setArchiveDate(date);
            archive.setReportData(reportData);
            salesArchiveRepository.save(archive);

            // Reset orders — delete items first, then orders
            entityManager.createNativeQuery("DELETE FROM order_items").executeUpdate();
            entityManager.createNativeQuery("DELETE FROM messages").executeUpdate();
            entityManager.createNativeQuery("DELETE FROM orders").executeUpdate();
            entityManager.createNativeQuery("ALTER TABLE orders AUTO_INCREMENT = 1").executeUpdate();
            entityManager.createNativeQuery("ALTER TABLE order_items AUTO_INCREMENT = 1").executeUpdate();

            return ResponseEntity.ok("Archived and reset successfully!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // Reset orders only — no archive save
    @DeleteMapping("/reset")
    @Transactional
    public ResponseEntity<?> resetOrdersOnly() {
        try {
            entityManager.createNativeQuery("DELETE FROM order_items").executeUpdate();
            entityManager.createNativeQuery("DELETE FROM messages").executeUpdate();
            entityManager.createNativeQuery("DELETE FROM orders").executeUpdate();
            entityManager.createNativeQuery("ALTER TABLE orders AUTO_INCREMENT = 1").executeUpdate();
            entityManager.createNativeQuery("ALTER TABLE order_items AUTO_INCREMENT = 1").executeUpdate();
            return ResponseEntity.ok("Orders reset successfully!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // Get all archived dates
    @GetMapping("/archives")
    public List<SalesArchive> getAllArchives() {
        return salesArchiveRepository.findAllByOrderByArchiveDateDesc();
    }

    // Get archive by date
    @GetMapping("/archives/{date}")
    public ResponseEntity<?> getArchiveByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return salesArchiveRepository.findByArchiveDate(localDate)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}