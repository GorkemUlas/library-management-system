package com.lms.backend.library.service;

import com.lms.backend.library.dto.UserDto;
import com.lms.backend.library.dto.UserSummaryDto;
import com.lms.backend.library.entity.Loan;
import com.lms.backend.library.entity.Hold;

import com.lms.backend.library.entity.User;
import com.lms.backend.library.mapper.UserMapper;
import com.lms.backend.library.repository.HoldRepository;
import com.lms.backend.library.repository.UserRepository;
import com.lms.backend.library.repository.LoanRepository;

import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final LoanRepository loanRepository;
    private final HoldRepository holdRepository;

    public UserService(UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            LoanRepository loanRepository,
            HoldRepository holdRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.loanRepository = loanRepository;
        this.holdRepository = holdRepository;
    }

    /**
     * Register / create user with email uniqueness checks and normalization.
     * Throws EmailAlreadyUsedException on conflict.
     */
    @Transactional
    public User createUser(UserDto dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User payload is required");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }
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
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User getUserByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        String normalizedEmail = email.trim().toLowerCase();
        return userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Aktif Loan kontrolü
        boolean hasActiveLoans = loanRepository.existsByUser_UserIdAndStatus(userId, Loan.LoanStatus.ACTIVE);

        // ✅Aktif Hold kontrolü
        boolean hasActiveHolds = holdRepository.existsByUser_UserIdAndStatus(userId, Hold.HoldStatus.PENDING);

        if (hasActiveLoans || hasActiveHolds) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "User cannot be deleted because they have active loans or holds.");
        }

        userRepository.delete(user);
    }

    /**
     * Runtime exception used to signal email conflict.
     * Kept as an inner class for convenience; you can move it to its own file if
     * preferred.
     */
    public static class EmailAlreadyUsedException extends RuntimeException {
        public EmailAlreadyUsedException(String message) {
            super(message);
        }

        public EmailAlreadyUsedException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public UserSummaryDto getUserSummary(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        int totalLoans = loanRepository.countByUser_UserId(id);
        int activeLoans = loanRepository.countByUser_UserIdAndStatus(id, Loan.LoanStatus.ACTIVE);
        UserSummaryDto dto = new UserSummaryDto();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setTotalLoans(totalLoans);
        dto.setActiveLoans(activeLoans);

        return dto;
    }

    public void changeUserRole(Long userId, String newRole) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!newRole.equals("USER") && !newRole.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role");
        }
        // Aktif Loan kontrolü
        boolean hasActiveLoans = loanRepository.existsByUser_UserIdAndStatus(userId, Loan.LoanStatus.ACTIVE);

        // ✅Aktif Hold kontrolü
        boolean hasActiveHolds = holdRepository.existsByUser_UserIdAndStatus(userId, Hold.HoldStatus.PENDING);

        if (hasActiveLoans || hasActiveHolds) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "User cannot be deleted because they have active loans or holds.");
        }

        user.setRole(newRole);
        userRepository.save(user);
    }

}
