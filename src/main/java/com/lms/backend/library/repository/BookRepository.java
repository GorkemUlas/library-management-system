package com.lms.backend.library.repository;
import com.lms.backend.library.entity.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Örn: kategoriye göre kitap bul
    java.util.List<Book> findByCategory(String category);


    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByCategoryContainingIgnoreCase(String category);

    boolean existsByIsbn(String isbn);
}