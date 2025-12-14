package com.lms.backend.library.service;

import com.lms.backend.library.dto.BookRequestDto;
import com.lms.backend.library.dto.BookResponseDto;
import com.lms.backend.library.entity.Book;
import com.lms.backend.library.exception.BadRequestException;
import com.lms.backend.library.exception.DuplicateIsbnException;
import com.lms.backend.library.mapper.BookMapper;
import com.lms.backend.library.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

        // DTO -> entity (isbn, title, author, totalCopies, imageUrl, category)
        Book book = bookMapper.toEntity(dto);

        // Business rules
        if (book.getTotalCopies() == null || book.getTotalCopies() < 0) {
            throw new IllegalArgumentException("totalCopies must be provided and >= 0");
        }

        // initial availableCopies = totalCopies
        book.setAvailableCopies(book.getTotalCopies());

        // status based on availableCopies
        book.setStatus( book.getAvailableCopies() !=null && book.getAvailableCopies()  > 0 ? Book.BookStatus.AVAILABLE : Book.BookStatus.NOT_AVAILABLE);
        // Eğer enum kullanıyorsan: book.setStatus(BookStatus.AVAILABLE);

        //ISBN kontrol
        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new DuplicateIsbnException(dto.getIsbn());
        }
        System.out.println("Mapped ISBN = " + book.getIsbn()); // null mu?

        bookRepository.save(book);
        return bookMapper.toResponseDto(book);
    }




    @Transactional
    public BookResponseDto updateBook(Long id, BookRequestDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Book not found with id: " + id));

        // Temel alanlar
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setCategory(dto.getCategory());
        book.setImageUrl(dto.getImageUrl());

        // ISBN kontrolü ve setleme
        if (dto.getIsbn() != null && !dto.getIsbn().equals(book.getIsbn())) {
            if (bookRepository.existsByIsbn(dto.getIsbn())) {
                throw new BadRequestException("ISBN already exists: " + dto.getIsbn());
            }
            book.setIsbn(dto.getIsbn());
        }

        // totalCopies değişimi mantığı
        if (dto.getTotalCopies() != null && !dto.getTotalCopies().equals(book.getTotalCopies())) {
            int oldTotal = book.getTotalCopies() == null ? 0 : book.getTotalCopies();
            int newTotal = dto.getTotalCopies();

            int oldAvailable = book.getAvailableCopies() == null ? 0 : book.getAvailableCopies();
            int borrowed = oldTotal - oldAvailable; // şu anda ödünç alınmış kopya sayısı

            if (newTotal < borrowed) {
                throw new BadRequestException("Cannot reduce total copies below currently borrowed amount (" + borrowed + ")");
            }

            // Eğer client explicit availableCopies göndermediyse, available'ı total farkına göre ayarla
            if (dto.getAvailableCopies() == null) {
                int difference = newTotal - oldTotal;
                book.setAvailableCopies(oldAvailable + difference);
            }

            book.setTotalCopies(newTotal);
        }

        // Eğer client availableCopies gönderdiyse, onu doğrula ve uygula
        if (dto.getAvailableCopies() != null) {
            int newAvailable = dto.getAvailableCopies();
            if (newAvailable < 0) {
                throw new BadRequestException("availableCopies cannot be negative");
            }
            int total = book.getTotalCopies() == null ? 0 : book.getTotalCopies();
            // borrowed = total - currentAvailable
            int currentAvailable = book.getAvailableCopies() == null ? 0 : book.getAvailableCopies();
            int borrowed = total - currentAvailable;

            // minimum allowed available = total - borrowed (yani mevcut ödünç alınmış)
            int minAvailableAllowed = total - borrowed;
            if (newAvailable < minAvailableAllowed) {
                throw new BadRequestException("availableCopies cannot be set below currently borrowed amount (" + borrowed + ")");
            }
            if (total > 0 && newAvailable > total) {
                throw new BadRequestException("availableCopies cannot exceed totalCopies");
            }

            book.setAvailableCopies(newAvailable);
        }

        // status güncellemesi (enum kullanıyorsan)
        if (book.getAvailableCopies() != null && book.getAvailableCopies() > 0) {
            book.setStatus(Book.BookStatus.AVAILABLE);
        } else {
            book.setStatus(Book.BookStatus.NOT_AVAILABLE);
        }

        Book saved = bookRepository.save(book);
        return bookMapper.toResponseDto(saved);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Book not found with id: " + id));

        bookRepository.delete(book);
    }



    public BookResponseDto getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Book not found with id: " + id));

        return bookMapper.toResponseDto(book);
    }


    public List<BookResponseDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
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

    public List<BookResponseDto> searchByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        return bookMapper.toResponseDtoList(books);
    }












    @Transactional
    public void borrowBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));

        Integer available = book.getAvailableCopies();
        if (available == null || available <= 0) {
            throw new IllegalStateException("No copies available");
        }

        book.setAvailableCopies(available - 1);

        // Enum kullanıyorsan BookStatus.AVAILABLE / NOT_AVAILABLE
        book.setStatus(book.getAvailableCopies() > 0 ? Book.BookStatus.AVAILABLE : Book.BookStatus.NOT_AVAILABLE);

        bookRepository.save(book);
    }

    @Transactional
    public void returnBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));

        int available = (book.getAvailableCopies() == null) ? 0 : book.getAvailableCopies();
        if (available < book.getTotalCopies()) {
            book.setAvailableCopies(available + 1);
        }

        book.setStatus(book.getAvailableCopies() > 0 ? Book.BookStatus.AVAILABLE : Book.BookStatus.NOT_AVAILABLE);
        bookRepository.save(book);
    }


}



