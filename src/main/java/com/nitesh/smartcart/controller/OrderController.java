package com.nitesh.smartcart.controller;

import com.nitesh.smartcart.entity.Order;
import com.nitesh.smartcart.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Place Order
    @PostMapping("/{userId}/place")
    public Order placeOrder(@PathVariable Integer userId) {
        return orderService.placeOrder(userId);
    }

    // Get Order By Id
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Integer orderId) {
        return orderService.getOrderById(orderId);
    }

    // Get All Orders Of User
    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Integer userId) {
        return orderService.getUserOrders(userId);
    }
}
