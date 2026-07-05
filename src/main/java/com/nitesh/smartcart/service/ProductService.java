package com.nitesh.smartcart.service;

import com.nitesh.smartcart.entity.Product;
import com.nitesh.smartcart.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get all active products
    public List<Product> getAllProducts() {
        return productRepository.findByActiveTrue();
    }

    // Get product by ID
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    // Add Product
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Update Product
    public Product updateProduct(Integer id, Product updatedProduct) {

        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct == null) {
            return null;
        }

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setBrand(updatedProduct.getBrand());
        existingProduct.setStock(updatedProduct.getStock());
        existingProduct.setActive(updatedProduct.getActive());
        existingProduct.setImageUrl(updatedProduct.getImageUrl());

        return productRepository.save(existingProduct);
    }

    // Soft Delete Product
    public boolean deleteProduct(Integer id) {

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return false;
        }

        product.setActive(false);
        productRepository.save(product);

        return true;
    }

    // Search by Category
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCaseAndActiveTrue(category);
    }

    // Search by Brand
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrandIgnoreCaseAndActiveTrue(brand);
    }

    // Search by Name
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCaseAndActiveTrue(keyword);
    }
}