package com.shoplite.shoplite.controller;

import com.shoplite.shoplite.model.Product;
import com.shoplite.shoplite.service.ReportService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // Daily Sales Report
    @GetMapping("/daily-sales")
    public ResponseEntity<Map<String, Object>> dailySales(
            @RequestParam String date) {

        LocalDate localDate =
                LocalDate.parse(date);

        return ResponseEntity.ok(
                reportService.getDailySales(localDate));
    }

    // Low Stock Report
    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> lowStock() {

        return ResponseEntity.ok(
                reportService.getLowStockProducts());
    }

    // Product Count
    @GetMapping("/product-count")
    public ResponseEntity<Long> productCount() {

        return ResponseEntity.ok(
                reportService.getProductCount());
    }

    // Bill Count
    @GetMapping("/bill-count")
    public ResponseEntity<Long> billCount() {

        return ResponseEntity.ok(
                reportService.getBillCount());
    }
}