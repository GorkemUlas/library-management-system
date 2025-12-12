package com.lms.backend.library.service;

import com.lms.backend.library.dto.BookRequestDto;
import com.lms.backend.library.dto.BookResponseDto;
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

    public BookResponseDto createBook(BookRequestDto dto) {
        Book book = bookMapper.toEntity(dto);
        bookRepository.save(book);
        return bookMapper.toResponseDto(book);
    }


    public BookResponseDto updateBook(Long id, BookRequestDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // MapStruct ile entity update
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setStatus(dto.getStatus());
        book.setCategory(dto.getCategory());

        bookRepository.save(book);
        return bookMapper.toResponseDto(book);
    }


    public Book getBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<BookResponseDto> getAllBooks() {
        return bookMapper.toResponseDtoList(bookRepository.findAll());
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        bookRepository.delete(book);
    }
}
