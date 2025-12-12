package com.lms.backend.library.repository;
import com.lms.backend.library.entity.Hold;

import org.springframework.data.jpa.repository.JpaRepository;

    public interface HoldRepository extends JpaRepository<Hold, Long> {
        // Örn: kitap id’sine göre hold bul
        java.util.List<Hold> findByBook_BookId(Long bookId);
    }