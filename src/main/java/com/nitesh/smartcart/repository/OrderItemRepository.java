package com.nitesh.smartcart.repository;

import com.nitesh.smartcart.entity.Order;
import com.nitesh.smartcart.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrder(Order order);

}