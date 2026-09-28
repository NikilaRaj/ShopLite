package com.shoplite.shoplite.service;

import com.shoplite.shoplite.model.Bill;
import com.shoplite.shoplite.model.Product;
import com.shoplite.shoplite.repository.BillRepository;
import com.shoplite.shoplite.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final BillRepository billRepository;
    private final ProductRepository productRepository;

    public ReportService(BillRepository billRepository,
                         ProductRepository productRepository) {
        this.billRepository = billRepository;
        this.productRepository = productRepository;
    }

    // Daily Sales Report
    public Map<String, Object> getDailySales(LocalDate date) {

        List<Bill> bills =
                billRepository.findByBillDate(date);

        double totalSales = bills.stream()
                .mapToDouble(Bill::getTotalAmount)
                .sum();

        Map<String, Object> report = new HashMap<>();

        report.put("date", date);
        report.put("numberOfBills", bills.size());
        report.put("totalSales", totalSales);

        return report;
    }

    // Low Stock Report
    public List<Product> getLowStockProducts() {

        List<Product> products =
                productRepository.findAll();

        return products.stream()
                .filter(p ->
                        p.getStockQuantity()
                                <= p.getReorderThreshold())
                .toList();
    }

    // Total Product Count
    public long getProductCount() {

        return productRepository.count();
    }

    // Total Bill Count
    public long getBillCount() {

        return billRepository.count();
    }
}