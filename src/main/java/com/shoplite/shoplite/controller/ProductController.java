package com.shoplite.shoplite.controller;

import com.shoplite.shoplite.model.Product;
import com.shoplite.shoplite.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // ADD
    @PostMapping
    public ResponseEntity<Product> addProduct(
            @Valid @RequestBody Product product) {

        return ResponseEntity.ok(
                service.addProduct(product));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {

        return ResponseEntity.ok(
                service.getAllProducts());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getProduct(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product) {

        return ResponseEntity.ok(
                service.updateProduct(id, product));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long id) {

        service.deleteProduct(id);

        return ResponseEntity.ok(
                "Product deleted successfully");
    }

    // LOW STOCK
    @GetMapping("/reorder")
    public ResponseEntity<List<Product>>
    getLowStockProducts() {

        return ResponseEntity.ok(
                service.getLowStockProducts());
    }
}