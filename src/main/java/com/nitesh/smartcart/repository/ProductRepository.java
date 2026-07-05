package com.nitesh.smartcart.repository;

import com.nitesh.smartcart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByActiveTrue();

    List<Product> findByCategoryIgnoreCaseAndActiveTrue(String category);

    List<Product> findByBrandIgnoreCaseAndActiveTrue(String brand);

    List<Product> findByNameContainingIgnoreCaseAndActiveTrue(String keyword);

}