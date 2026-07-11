package com.nitesh.smartcart.controller;

import com.nitesh.smartcart.dto.ApiResponse;
import com.nitesh.smartcart.dto.ProductRequest;
import com.nitesh.smartcart.dto.ProductResponse;
import com.nitesh.smartcart.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get All Products
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get Product By ID
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    // Add Product
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductResponse addProduct(@Valid @RequestBody ProductRequest request) {
        return productService.addProduct(request);
    }

    // Update Product
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Integer id,
                                         @Valid @RequestBody ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    // Soft Delete Product
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse deleteProduct(@PathVariable Integer id) {

        productService.deleteProduct(id);

        return new ApiResponse("Product deleted successfully.");
    }

    // Get Products By Category
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/category/{category}")
    public List<ProductResponse> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }

    // Get Products By Brand
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/brand/{brand}")
    public List<ProductResponse> getProductsByBrand(@PathVariable String brand) {
        return productService.getProductsByBrand(brand);
    }

    // Search Products
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/search/{keyword}")
    public List<ProductResponse> searchProducts(@PathVariable String keyword) {
        return productService.searchProducts(keyword);
    }
}