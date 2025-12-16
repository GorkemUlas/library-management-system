package com.lms.backend.library.service;

import com.lms.backend.library.dto.HoldDto;
import com.lms.backend.library.dto.LoanResponseDto;
import com.lms.backend.library.entity.Hold;
import com.lms.backend.library.mapper.LoanMapper;
import com.lms.backend.library.repository.BookRepository;
import com.lms.backend.library.repository.HoldRepository;
import com.lms.backend.library.repository.UserRepository;
import com.lms.backend.library.dto.LoanDto;
import com.lms.backend.library.entity.Book;
import com.lms.backend.library.entity.User;
import com.lms.backend.library.entity.Loan;
import com.lms.backend.library.repository.LoanRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final LoanMapper loanMapper;
    private final HoldService holdService;
    private final HoldRepository holdRepository;

    public LoanService(LoanRepository loanRepository,
            UserRepository userRepository,
            BookRepository bookRepository,
            LoanMapper loanMapper,
            HoldService holdService,
            HoldRepository holdRepository) {

        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.loanMapper = loanMapper;
        this.holdService = holdService;
        this.holdRepository = holdRepository;
    }

    public LoanResponseDto createLoan(LoanDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setIssueDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));
        loan.setReturnDate(null);
        loan.setFineAmount(0.0);
        loan.setStatus(Loan.LoanStatus.ACTIVE);

        // stok düş
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        if (book.getAvailableCopies() == 0) {
            book.setStatus(Book.BookStatus.NOT_AVAILABLE);
        }
        bookRepository.save(book);

        Loan saved = loanRepository.save(loan);
        return loanMapper.toResponseDto(saved);
    }

    public LoanResponseDto getLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        return loanMapper.toResponseDto(loan);
    }

    public List<LoanResponseDto> getAllLoans() {
        List<Loan> loans = loanRepository.findAll();
        return loanMapper.toResponseDtoList(loans);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    public LoanResponseDto borrowBook(LoanDto dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        boolean alreadyBorrowed = loanRepository
                .existsByUser_UserIdAndBook_BookIdAndStatus(
                        dto.getUserId(), dto.getBookId(), Loan.LoanStatus.ACTIVE);

        if (alreadyBorrowed) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You already borrowed this book");
        }

        // 📌 STOK YOK → HOLD
        if (book.getAvailableCopies() <= 0) {

            HoldDto holdDto = new HoldDto();
            holdDto.setUserId(dto.getUserId());
            holdDto.setBookId(dto.getBookId());

            holdService.createHold(holdDto);

            LoanResponseDto response = new LoanResponseDto();
            response.setBookId(book.getBookId());
            response.setBookTitle(book.getTitle());
            response.setBookImage(book.getImageUrl());

            response.setStatus("HOLD_PLACED");
            response.setMessage("Book is not available. You have been added to the hold queue.");

            return response;
        }

        // 📌 BORROW
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setIssueDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));
        loan.setFineAmount(0.0);
        loan.setStatus(Loan.LoanStatus.ACTIVE);

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        if (book.getAvailableCopies() == 0) {
            book.setStatus(Book.BookStatus.NOT_AVAILABLE);
        }
        bookRepository.save(book);

        Loan saved = loanRepository.save(loan);

        LoanResponseDto response = loanMapper.toResponseDto(saved);
        response.setStatus("BORROWED");
        response.setMessage("Book borrowed successfully");

        return response;
    }

    public LoanResponseDto returnBook(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));

        if (loan.getStatus() == Loan.LoanStatus.RETURNED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book already returned");
        }

        Book book = loan.getBook();

        // HOLD SIRASINI KONTROL ET
        List<Hold> holds = holdRepository.findByBook_BookIdAndStatusOrderByHoldDateAsc(
                book.getBookId(), Hold.HoldStatus.PENDING);

        if (!holds.isEmpty()) {
            Hold next = holds.get(0);

            next.setStatus(Hold.HoldStatus.COMPLETED);
            holdRepository.save(next);

            // Otomatik Loan
            LoanDto autoLoan = new LoanDto();
            autoLoan.setUserId(next.getUser().getUserId());
            autoLoan.setBookId(book.getBookId());

            createLoan(autoLoan);
        }

        // Loan güncelle
        loan.setReturnDate(LocalDate.now());
        loan.setStatus(Loan.LoanStatus.RETURNED);

        // Gecikme kontrolü
        LocalDate dueDate = loan.getIssueDate().plusDays(14);

        if (loan.getReturnDate().isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, loan.getReturnDate());
            loan.setFineAmount(daysLate * 20.0);
        } else {
            loan.setFineAmount(0.0);
        }

        // stok artır
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        book.setStatus(Book.BookStatus.AVAILABLE);
        bookRepository.save(book);

        Loan saved = loanRepository.save(loan);
        return loanMapper.toResponseDto(saved);
    }

    public List<LoanResponseDto> getActiveLoans(Long userId) {
        List<Loan> loans = loanRepository.findByUser_UserIdAndStatus(userId, Loan.LoanStatus.ACTIVE);
        return loanMapper.toResponseDtoList(loans);
    }

    public List<LoanResponseDto> getLoanHistory(Long userId) {
        List<Loan> loans = loanRepository.findByUser_UserIdAndStatus(userId, Loan.LoanStatus.RETURNED);
        return loanMapper.toResponseDtoList(loans);
    }

}
