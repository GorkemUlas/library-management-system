package com.lms.backend.library.service;

import com.lms.backend.library.entity.User;
import com.lms.backend.library.exception.InvalidCredentialsException;
import com.lms.backend.library.repository.UserRepository;
import com.lms.backend.library.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    // REGISTER
    public String register(String name, String email, String password) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        user.setFine_balance(0.0);

        userRepository.save(user);

        return jwtService.generateTokenFromUser(user);
    }

    // LOGIN
    public String login(String email, String password) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new InvalidCredentialsException("Email veya şifre hatalı");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
        return jwtService.generateToken(userDetails);
    }
}
