package com.messycafe.delivery_system.repository;

import com.messycafe.delivery_system.model.SalesArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SalesArchiveRepository extends JpaRepository<SalesArchive, Long> {
    Optional<SalesArchive> findByArchiveDate(LocalDate date);
    List<SalesArchive> findAllByOrderByArchiveDateDesc();
}