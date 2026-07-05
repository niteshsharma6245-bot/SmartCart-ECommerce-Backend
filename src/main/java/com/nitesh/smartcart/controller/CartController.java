package com.nitesh.smartcart.controller;

import com.nitesh.smartcart.entity.Cart;
import com.nitesh.smartcart.entity.CartItem;
import com.nitesh.smartcart.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Get Cart By User
    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Integer userId) {
        return cartService.getCartByUser(userId);
    }

    // Get All Cart Items
    @GetMapping("/{cartId}/items")
    public List<CartItem> getCartItems(@PathVariable Integer cartId) {
        return cartService.getCartItems(cartId);
    }

    // Add Product To Cart
    @PostMapping("/{userId}/add")
    public CartItem addProductToCart(
            @PathVariable Integer userId,
            @RequestParam Integer productId,
            @RequestParam Integer quantity) {

        return cartService.addProductToCart(userId, productId, quantity);
    }

    // Update Quantity
    @PutMapping("/item/{cartItemId}")
    public CartItem updateQuantity(
            @PathVariable Integer cartItemId,
            @RequestParam Integer quantity) {

        return cartService.updateQuantity(cartItemId, quantity);
    }

    // Remove Product
    @DeleteMapping("/item/{cartItemId}")
    public String removeProduct(@PathVariable Integer cartItemId) {

        boolean deleted = cartService.removeProduct(cartItemId);

        if (deleted) {
            return "Product removed from cart.";
        }

        return "Cart item not found.";
    }
}