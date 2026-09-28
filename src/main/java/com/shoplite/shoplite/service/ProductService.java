package com.shoplite.shoplite.service;

import com.shoplite.shoplite.exception.ResourceNotFoundException;
import com.shoplite.shoplite.model.Product;
import com.shoplite.shoplite.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // Add Product
    public Product addProduct(Product product) {
        return repository.save(product);
    }

    // Get All Products
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    // Get Product by ID
    public Product getProduct(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found: " + id));
    }

    // Update Product
    public Product updateProduct(
            Long id,
            Product newProduct) {

        Product product = getProduct(id);

        product.setName(newProduct.getName());
        product.setPrice(newProduct.getPrice());
        product.setStockQuantity(
                newProduct.getStockQuantity());
        product.setReorderThreshold(
                newProduct.getReorderThreshold());

        return repository.save(product);
    }

    // Delete Product
    public void deleteProduct(Long id) {

        Product product = getProduct(id);

        repository.delete(product);
    }

    // Low Stock Products
    public List<Product> getLowStockProducts() {

        return repository.findByStockQuantityLessThan(
                10);
    }
}