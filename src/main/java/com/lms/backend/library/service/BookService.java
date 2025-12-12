package com.lms.backend.library.service;

import com.lms.backend.library.dto.BookDto;
import com.lms.backend.library.entity.Book;
import com.lms.backend.library.mapper.BookMapper;
import com.lms.backend.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository , BookMapper bookMapper) {

        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    /*public Book addBook(Book book) {
        return bookRepository.save(book);
    }*/

    public Book createBook(BookDto dto) {
        // MapStruct otomatik dönüşüm
        Book book = bookMapper.toEntity(dto);
        return bookRepository.save(book);
    }


    public Book getBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
