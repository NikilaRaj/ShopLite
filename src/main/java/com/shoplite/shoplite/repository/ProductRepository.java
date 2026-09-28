package com.shoplite.shoplite.repository;

import com.shoplite.shoplite.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

    List<Product> findByStockQuantityLessThan(Integer threshold);
}