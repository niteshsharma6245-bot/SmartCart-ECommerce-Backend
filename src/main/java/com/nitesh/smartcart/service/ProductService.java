package com.nitesh.smartcart.service;

import com.nitesh.smartcart.dto.ProductRequest;
import com.nitesh.smartcart.dto.ProductResponse;
import com.nitesh.smartcart.entity.Product;
import com.nitesh.smartcart.exception.ProductNotFoundException;
import com.nitesh.smartcart.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService
{

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // =========================
    // Entity -> DTO
    // =========================

    private ProductResponse mapToResponse(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getBrand(),
                product.getStock(),
                product.getActive(),
                product.getImageUrl()
        );
    }

    // =========================
    // DTO -> Entity
    // =========================

    private Product mapToEntity(ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setBrand(request.getBrand());
        product.setStock(request.getStock());
        product.setActive(request.getActive());
        product.setImageUrl(request.getImageUrl());

        return product;
    }

    // =========================
    // Get All Products
    // =========================

    public List<ProductResponse> getAllProducts() {

        return productRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // =========================
    // Get Product By Id
    // =========================

    public ProductResponse getProductById(Integer id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id : " + id));

        return mapToResponse(product);
    }

    // =========================
    // Add Product
    // =========================

    public ProductResponse addProduct(ProductRequest request) {

        Product product = mapToEntity(request);

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }
    // =========================
    // Update Product
    // =========================

    public ProductResponse updateProduct(Integer id, ProductRequest request) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id : " + id));

        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setCategory(request.getCategory());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setStock(request.getStock());
        existingProduct.setActive(request.getActive());
        existingProduct.setImageUrl(request.getImageUrl());

        Product updatedProduct = productRepository.save(existingProduct);

        return mapToResponse(updatedProduct);
    }

    // =========================
    // Soft Delete Product
    // =========================

    public boolean deleteProduct(Integer id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id : " + id));

        product.setActive(false);
        productRepository.save(product);

        return true;
    }

    // =========================
    // Get Products By Category
    // =========================

    public List<ProductResponse> getProductsByCategory(String category) {

        return productRepository.findByCategoryIgnoreCaseAndActiveTrue(category)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // =========================
    // Get Products By Brand
    // =========================

    public List<ProductResponse> getProductsByBrand(String brand) {

        return productRepository.findByBrandIgnoreCaseAndActiveTrue(brand)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // =========================
    // Search Products
    // =========================

    public List<ProductResponse> searchProducts(String keyword) {

        return productRepository
                .findByNameContainingIgnoreCaseAndActiveTrue(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
