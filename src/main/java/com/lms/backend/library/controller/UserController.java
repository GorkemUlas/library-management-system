package com.lms.backend.library.controller;

import com.lms.backend.library.dto.UserDto;
import com.lms.backend.library.entity.User;
import com.lms.backend.library.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
     * @PostMapping
     * public User createUser(@RequestBody User user) {
     * return userService.addUser(user);
     * }
     */

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto dto) {
        User created = userService.createUser(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/by-id/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/by-email")
    public User getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}