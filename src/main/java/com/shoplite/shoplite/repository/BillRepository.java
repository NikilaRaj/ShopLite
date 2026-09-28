package com.shoplite.shoplite.repository;

import com.shoplite.shoplite.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BillRepository
        extends JpaRepository<Bill, Long> {

    List<Bill> findByBillDate(LocalDate date);
}