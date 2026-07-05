package com.nitesh.smartcart.service;

import com.nitesh.smartcart.entity.*;
import com.nitesh.smartcart.repository.CartItemRepository;
import com.nitesh.smartcart.repository.CartRepository;
import com.nitesh.smartcart.repository.ProductRepository;
import com.nitesh.smartcart.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       ProductRepository productRepository,
                       UserRepository userRepository) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Get Cart By User Id
    public Cart getCartByUser(Integer userId) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return null;
        }

        return cartRepository.findByUser(user).orElse(null);
    }

    // Get All Cart Items
    public List<CartItem> getCartItems(Integer cartId) {

        Cart cart = cartRepository.findById(cartId).orElse(null);

        if (cart == null) {
            return null;
        }

        return cartItemRepository.findByCart(cart);
    }

    // Add Product To Cart
    public CartItem addProductToCart(Integer userId,
                                     Integer productId,
                                     Integer quantity) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return null;
        }

        Cart cart = cartRepository.findByUser(user).orElse(null);

        if (cart == null) {
            return null;
        }

        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            return null;
        }
        if (quantity <= 0) {
            return null;
        }

        if (product.getStock() < quantity) {
            return null;
        }

        CartItem existingItem =
                cartItemRepository.findByCartAndProduct(cart, product).orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            return cartItemRepository.save(existingItem);
        }

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }

    // Update Quantity
    public CartItem updateQuantity(Integer cartItemId, Integer quantity) {

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);

        if (cartItem == null) {
            return null;
        }

        if (quantity <= 0) {
            return null;
        }

        if (cartItem.getProduct().getStock() < quantity) {
            return null;
        }

        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }

    // Remove Product From Cart
    public boolean removeProduct(Integer cartItemId) {

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);

        if (cartItem == null) {
            return false;
        }

        cartItemRepository.delete(cartItem);

        return true;
    }
}