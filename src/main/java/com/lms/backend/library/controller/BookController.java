package com.lms.backend.library.controller;

import com.lms.backend.library.dto.BookDto;
import com.lms.backend.library.entity.Book;
import com.lms.backend.library.service.BookService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //silinecek addbook ile birlikte gereksiz overwrite
    /*@PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }*/

    // Yeni kitap ekleme
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookDto dto) {
        // @Valid → DTO üzerindeki validation kurallarını çalıştırır
        Book created = bookService.createBook(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}