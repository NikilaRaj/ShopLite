package com.shoplite.shoplite.controller;

import com.shoplite.shoplite.model.Bill;
import com.shoplite.shoplite.service.BillService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin(origins = "*")
public class BillController {

    private final BillService service;

    public BillController(BillService service) {
        this.service = service;
    }

    // CREATE / FINALIZE BILL
    @PostMapping
    public ResponseEntity<Bill> createBill(
            @RequestBody Bill bill) {

        return ResponseEntity.ok(
                service.createBill(bill));
    }

    // GET ALL BILLS
    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills() {

        return ResponseEntity.ok(
                service.getAllBills());
    }

    // GET BILL BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBill(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getBill(id));
    }

    // DAILY SALES
    @GetMapping("/sales")
    public ResponseEntity<?> dailySales(
            @RequestParam String date) {

        LocalDate localDate =
                LocalDate.parse(date);

        Double total =
                service.getDailySales(localDate);

        return ResponseEntity.ok(
                Map.of(
                        "date", date,
                        "totalSales", total
                ));
    }
}