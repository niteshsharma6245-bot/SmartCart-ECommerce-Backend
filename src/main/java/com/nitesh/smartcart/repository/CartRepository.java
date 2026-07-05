package com.nitesh.smartcart.repository;

import com.nitesh.smartcart.entity.Cart;
import com.nitesh.smartcart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUser(User user);

}
