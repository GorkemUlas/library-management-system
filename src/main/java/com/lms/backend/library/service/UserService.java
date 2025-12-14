package com.lms.backend.library.service;

import com.lms.backend.library.dto.UserDto;
import com.lms.backend.library.entity.User;
import com.lms.backend.library.mapper.UserMapper;
import com.lms.backend.library.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register / create user with email uniqueness checks and normalization.
     * Throws EmailAlreadyUsedException on conflict.
     */
    @Transactional
    public User createUser(UserDto dto) {
        String normalizedEmail = dto.getEmail().trim().toLowerCase();

        // hızlı ön kontrol
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyUsedException("Email already in use: " + normalizedEmail);
        }

        // DTO -> entity
        User user = userMapper.toEntity(dto);

        // kesinlikle normalized email'i entity'ye setle (mapper üzerine yazmasın)
        user.setEmail(normalizedEmail);

        // password encode
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            // DB unique constraint tetiklenirse yarış durumunu burada yakala
            throw new EmailAlreadyUsedException("Email already in use: " + normalizedEmail, ex);
        }


    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Runtime exception used to signal email conflict.
     * Kept as an inner class for convenience; you can move it to its own file if preferred.
     */
    public static class EmailAlreadyUsedException extends RuntimeException {
        public EmailAlreadyUsedException(String message) {
            super(message);
        }
        public EmailAlreadyUsedException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
