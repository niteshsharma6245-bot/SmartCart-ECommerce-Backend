package com.nitesh.smartcart.service;

import com.nitesh.smartcart.entity.Cart;
import com.nitesh.smartcart.entity.User;
import com.nitesh.smartcart.repository.CartRepository;
import com.nitesh.smartcart.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public UserService(UserRepository userRepository,
                       CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    // Get All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get User By Id
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    // Add User
    public User addUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            return null;
        }

        User savedUser = userRepository.save(user);

        // Automatically create a cart for every new user
        Cart cart = new Cart();
        cart.setUser(savedUser);

        cartRepository.save(cart);

        return savedUser;
    }

    // Update User
    public User updateUser(Integer id, User updatedUser) {

        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return null;
        }

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setEnabled(updatedUser.getEnabled());

        return userRepository.save(existingUser);
    }

    // Delete User
    public boolean deleteUser(Integer id) {

        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return false;
        }

        userRepository.delete(existingUser);

        return true;
    }
}