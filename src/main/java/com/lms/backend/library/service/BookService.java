package com.lms.backend.library.service;

import com.lms.backend.library.dto.BookRequestDto;
import com.lms.backend.library.dto.BookResponseDto;
import com.lms.backend.library.entity.Book;
import com.lms.backend.library.exception.DuplicateIsbnException;
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

        // stok baslangıcı
        book.setAvailableCopies(dto.getTotalCopies());

        //ISBN kontrol
        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new DuplicateIsbnException(dto.getIsbn());
        }

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

        // stok mantığı
        if (!book.getTotalCopies().equals(dto.getTotalCopies())) {

            int difference = dto.getTotalCopies() - book.getTotalCopies();
            int newAvailable = book.getAvailableCopies() + difference;

            if (newAvailable < 0) {
                throw new RuntimeException("Cannot reduce total copies below borrowed amount");
            }

            book.setTotalCopies(dto.getTotalCopies());
            book.setAvailableCopies(newAvailable);
        }

        // Eğer ISBN değişiyorsa ve yeni ISBN başka kitapta varsa hata
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book could not be found"));

        if (!existing.getIsbn().equals(dto.getIsbn()) &&
                bookRepository.existsByIsbn(dto.getIsbn())) {

            throw new DuplicateIsbnException(dto.getIsbn());
        }

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
    public List<BookResponseDto> searchByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        return bookMapper.toResponseDtoList(books);
    }

    public List<BookResponseDto> searchByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(author);
        return bookMapper.toResponseDtoList(books);
    }

    public List<BookResponseDto> searchByCategory(String category) {
        List<Book> books = bookRepository.findByCategoryContainingIgnoreCase(category);
        return bookMapper.toResponseDtoList(books);
    }



}
