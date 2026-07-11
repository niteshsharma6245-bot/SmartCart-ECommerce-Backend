package com.nitesh.smartcart.controller;

import com.nitesh.smartcart.dto.ApiResponse;
import com.nitesh.smartcart.dto.UserRequest;
import com.nitesh.smartcart.dto.UserResponse;
import com.nitesh.smartcart.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get All Users
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get User By ID
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    // Add User
    @PostMapping
    public UserResponse addUser(@Valid @RequestBody UserRequest request) {
        return userService.addUser(request);
    }

    // Update User
    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Integer id,
                                   @Valid @RequestBody UserRequest request) {
        return userService.updateUser(id, request);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ApiResponse deleteUser(@PathVariable Integer id) {

        userService.deleteUser(id);

        return new ApiResponse("User deleted successfully.");
    }
}