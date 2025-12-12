package com.lms.backend.library.repository;
import com.lms.backend.library.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Örn: kategoriye göre kitap bul
    java.util.List<Book> findByCategory(String category);
}