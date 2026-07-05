package com.nitesh.smartcart.service;

import com.nitesh.smartcart.entity.User;
import com.nitesh.smartcart.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        return userRepository.save(user);
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