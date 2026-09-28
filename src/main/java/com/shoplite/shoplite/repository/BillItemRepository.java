package com.shoplite.shoplite.repository;

import com.shoplite.shoplite.model.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillItemRepository
        extends JpaRepository<BillItem, Long> {
}