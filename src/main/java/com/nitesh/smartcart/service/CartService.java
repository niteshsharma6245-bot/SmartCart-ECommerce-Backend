package com.nitesh.smartcart.service;

import com.nitesh.smartcart.entity.Cart;
import com.nitesh.smartcart.entity.CartItem;
import com.nitesh.smartcart.entity.Product;
import com.nitesh.smartcart.entity.User;
import com.nitesh.smartcart.exception.CartNotFoundException;
import com.nitesh.smartcart.exception.ProductNotFoundException;
import com.nitesh.smartcart.exception.UserNotFoundException;
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

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id : " + userId));

        return cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new CartNotFoundException("Cart not found for user id : " + userId));
    }

    // Get All Cart Items
    public List<CartItem> getCartItems(Integer cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() ->
                        new CartNotFoundException("Cart not found with id : " + cartId));

        return cartItemRepository.findByCart(cart);
    }

    // Add Product To Cart
    public CartItem addProductToCart(Integer userId,
                                     Integer productId,
                                     Integer quantity) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id : " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new CartNotFoundException("Cart not found for user id : " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id : " + productId));

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Insufficient stock available.");
        }

        CartItem existingItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);

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

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new CartNotFoundException("Cart item not found with id : " + cartItemId));

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        if (cartItem.getProduct().getStock() < quantity) {
            throw new IllegalArgumentException("Insufficient stock available.");
        }

        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }

    // Remove Product From Cart
    public boolean removeProduct(Integer cartItemId) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new CartNotFoundException("Cart item not found with id : " + cartItemId));

        cartItemRepository.delete(cartItem);

        return true;
    }
}