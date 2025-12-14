package com.lms.backend.library.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users", schema = "dbo")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;
    private String name;
    private String password;
    private String role;
    private Double fine_balance;

    // -------------------------
    // SPRING SECURITY EKLEMELERİ
    // -------------------------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Şimdilik rol sistemi yok, boş liste döndürüyoruz
        return List.of();
    }

    @Override
    public String getUsername() {
        // Login email ile yapılacak
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Hesap süresi dolmaz
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Hesap kilitlenmez
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Şifre süresi dolmaz
    }

    @Override
    public boolean isEnabled() {
        return true; // Kullanıcı aktif
    }

    // -------------------------
    // GETTER - SETTER
    // -------------------------

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password; // UserDetails bunu ister
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Double getFine_balance() {
        return fine_balance;
    }

    public void setFine_balance(Double fine_balance) {
        this.fine_balance = fine_balance;
    }
}
