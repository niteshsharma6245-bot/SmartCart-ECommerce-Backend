package com.nitesh.smartcart.controller;

import com.nitesh.smartcart.entity.Product;
import com.nitesh.smartcart.service.ProductService;
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
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get Product By ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    // Add Product
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    // Update Product
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Integer id,
                                 @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    // Soft Delete Product
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Integer id) {

        boolean deleted = productService.deleteProduct(id);

        if (deleted) {
            return "Product deleted successfully.";
        }

        return "Product not found.";
    }

    // Get Products By Category
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }

    // Get Products By Brand
    @GetMapping("/brand/{brand}")
    public List<Product> getProductsByBrand(@PathVariable String brand) {
        return productService.getProductsByBrand(brand);
    }

    // Search Products
    @GetMapping("/search/{keyword}")
    public List<Product> searchProducts(@PathVariable String keyword) {
        return productService.searchProducts(keyword);
    }
}