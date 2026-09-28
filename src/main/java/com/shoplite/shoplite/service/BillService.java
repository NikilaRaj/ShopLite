package com.shoplite.shoplite.service;

import com.shoplite.shoplite.exception.InsufficientStockException;
import com.shoplite.shoplite.exception.ResourceNotFoundException;
import com.shoplite.shoplite.model.Bill;
import com.shoplite.shoplite.model.BillItem;
import com.shoplite.shoplite.model.Product;
import com.shoplite.shoplite.repository.BillRepository;
import com.shoplite.shoplite.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final ProductRepository productRepository;

    public BillService(
            BillRepository billRepository,
            ProductRepository productRepository) {

        this.billRepository = billRepository;
        this.productRepository = productRepository;
    }

    // Create Bill
    @Transactional
    public Bill createBill(Bill bill) {

        double total = 0;

        for (BillItem item : bill.getItems()) {

            Product product =
                    productRepository.findById(
                                    item.getProduct().getId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Product not found"));

            // Check stock
            if (item.getQuantity()
                    > product.getStockQuantity()) {

                throw new InsufficientStockException(
                        "Insufficient stock for product: "
                                + product.getName());
            }

            // Use current product price
            item.setProduct(product);
            item.setPrice(product.getPrice());
            item.setBill(bill);

            total +=
                    product.getPrice()
                            * item.getQuantity();
        }

        bill.setBillDate(LocalDate.now());
        bill.setTotalAmount(total);

        // Reduce stock only when bill is finalized
        for (BillItem item : bill.getItems()) {

            Product product = item.getProduct();

            product.setStockQuantity(
                    product.getStockQuantity()
                            - item.getQuantity());

            productRepository.save(product);
        }

        return billRepository.save(bill);
    }

    // Get all bills
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    // Get bill
    public Bill getBill(Long id) {

        return billRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bill not found: " + id));
    }

    // Daily sales
    public Double getDailySales(LocalDate date) {

        List<Bill> bills =
                billRepository.findByBillDate(date);

        return bills.stream()
                .mapToDouble(Bill::getTotalAmount)
                .sum();
    }
}