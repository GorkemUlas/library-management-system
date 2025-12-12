package com.lms.backend.library.service;

import com.lms.backend.library.dto.UserDto;
import com.lms.backend.library.entity.User;
import com.lms.backend.library.mapper.UserMapper;
import com.lms.backend.library.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User createUser(UserDto dto) {
        User user = userMapper.toEntity(dto);
        return userRepository.save(user);
    }

    /*public User addUser(User user) {
        return userRepository.save(user);
    }*/
    /*public User createUser(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return userRepository.save(user);
    }*/

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}