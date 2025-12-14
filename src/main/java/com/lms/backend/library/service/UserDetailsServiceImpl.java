package com.lms.backend.library.service;

import com.lms.backend.library.entity.User;
import com.lms.backend.library.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security login sırasında kullanıcıyı buradan bulur.
 * Biz email ile login olacağımız için loadUserByUsername(email) yazıyoruz.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        String role = user.getRole().trim().toUpperCase(); // normalize
//
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getEmail())
//                .password(user.getPassword())
//                .roles(role) // Spring → ROLE_ADMIN yapar
//                .build();
//    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        System.out.println("USER ROLE FROM DB = " + user.getRole()); // ✅ TEST

        String rawRole = user.getRole().trim().toUpperCase();
        String normalizedRole = rawRole.replace("ROLE_", ""); // -> "ADMIN"

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(normalizedRole) // Spring otomatik ROLE_ prefix ekler -> ROLE_ADMIN
                .build();

    }
    }
