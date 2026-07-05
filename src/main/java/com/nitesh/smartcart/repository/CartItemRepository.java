package com.nitesh.smartcart.repository;

import com.nitesh.smartcart.entity.Cart;
import com.nitesh.smartcart.entity.CartItem;
import com.nitesh.smartcart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCart(Cart cart);

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

}
