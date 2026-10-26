package com.messycafe.delivery_system.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "sales_archive")
public class SalesArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The date this archive belongs to
    @Column(name = "archive_date")
    private LocalDate archiveDate;

    // Summary data saved as JSON string
    @Column(name = "report_data", columnDefinition = "LONGTEXT")
    private String reportData;

    public SalesArchive() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getArchiveDate() { return archiveDate; }
    public void setArchiveDate(LocalDate archiveDate) { this.archiveDate = archiveDate; }

    public String getReportData() { return reportData; }
    public void setReportData(String reportData) { this.reportData = reportData; }
}