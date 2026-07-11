package com.nitesh.smartcart.service;

import com.nitesh.smartcart.dto.OrderItemResponse;
import com.nitesh.smartcart.dto.OrderResponse;
import com.nitesh.smartcart.entity.*;
import com.nitesh.smartcart.exception.CartNotFoundException;
import com.nitesh.smartcart.exception.OrderNotFoundException;
import com.nitesh.smartcart.exception.UserNotFoundException;
import com.nitesh.smartcart.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        CartRepository cartRepository,
                        CartItemRepository cartItemRepository,
                        UserRepository userRepository,
                        ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // =========================
    // Entity -> DTO Mapping
    // =========================

    private OrderItemResponse mapOrderItem(OrderItem orderItem) {

        return new OrderItemResponse(
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
    }

    private OrderResponse mapOrder(Order order) {

        List<OrderItemResponse> items = order.getOrderItems()
                .stream()
                .map(this::mapOrderItem)
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getOrderDate(),
                items
        );
    }

    // =========================
    // Place Order
    // =========================

    @Transactional
    public OrderResponse placeOrder(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id : " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new CartNotFoundException(
                                "Cart not found for user id : " + userId));

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        if (cartItems.isEmpty()) {
            throw new CartNotFoundException("Cart is empty.");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.ZERO);

        Order savedOrder = orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {

            Product product = cartItem.getProduct();

            if (product.getStock() < cartItem.getQuantity()) {
                throw new IllegalArgumentException(
                        "Insufficient stock for product: " + product.getName()
                );
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());

            orderItemRepository.save(orderItem);

            // Keep entity relationship synchronized
            savedOrder.getOrderItems().add(orderItem);

            totalAmount = totalAmount.add(
                    product.getPrice().multiply(
                            BigDecimal.valueOf(cartItem.getQuantity())
                    )
            );

            product.setStock(
                    product.getStock() - cartItem.getQuantity()
            );

            productRepository.save(product);
        }

        savedOrder.setTotalAmount(totalAmount);

        Order finalOrder = orderRepository.save(savedOrder);

        cartItemRepository.deleteAll(cartItems);

        return mapOrder(finalOrder);
    }

    // =========================
    // Get Order By Id
    // =========================

    public OrderResponse getOrderById(Integer orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found with id : " + orderId
                        )
                );

        return mapOrder(order);
    }

    // =========================
    // Get Orders Of User
    // =========================

    public List<OrderResponse> getUserOrders(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id : " + userId
                        )
                );

        List<Order> orders = orderRepository.findByUser(user);
        return orders.stream()
                .map(this::mapOrder)
                .collect(Collectors.toList());
    }
}


