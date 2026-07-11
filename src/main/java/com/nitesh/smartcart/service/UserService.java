package com.nitesh.smartcart.service;

import com.nitesh.smartcart.dto.UserRequest;
import com.nitesh.smartcart.dto.UserResponse;
import com.nitesh.smartcart.entity.Cart;
import com.nitesh.smartcart.entity.User;
import com.nitesh.smartcart.exception.UserNotFoundException;
import com.nitesh.smartcart.repository.CartRepository;
import com.nitesh.smartcart.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       CartRepository cartRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // Entity -> DTO
    // =========================

    private UserResponse mapToResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getEnabled()
        );
    }

    // =========================
    // DTO -> Entity
    // =========================

    private User mapToEntity(UserRequest request) {

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        // Password encryption
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setEnabled(request.getEnabled());

        return user;
    }

    // =========================
    // Get All Users
    // =========================

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // =========================
    // Get User By Id
    // =========================

    public UserResponse getUserById(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id : " + id));

        return mapToResponse(user);
    }

    // =========================
    // Add User
    // =========================

    public UserResponse addUser(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        User user = mapToEntity(request);

        User savedUser = userRepository.save(user);

        // Automatically create a cart for every new user
        Cart cart = new Cart();
        cart.setUser(savedUser);

        cartRepository.save(cart);

        return mapToResponse(savedUser);
    }
    // =========================
    // Update User
    // =========================

    public UserResponse updateUser(Integer id, UserRequest request) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id : " + id));

        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setEmail(request.getEmail());

        // Encrypt Password
        existingUser.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        existingUser.setPhone(request.getPhone());
        existingUser.setRole(request.getRole());
        existingUser.setEnabled(request.getEnabled());

        User updatedUser = userRepository.save(existingUser);

        return mapToResponse(updatedUser);
    }

    // =========================
    // Delete User
    // =========================

    public boolean deleteUser(Integer id) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id : " + id));

        userRepository.delete(existingUser);

        return true;
    }
}