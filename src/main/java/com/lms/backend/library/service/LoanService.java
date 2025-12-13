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
import org.springframework.stereotype.Service;

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

        Loan loan = loanMapper.toEntity(dto);

        loan.setUser(
                userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );

        loan.setBook(
                bookRepository.findById(dto.getBookId())
                        .orElseThrow(() -> new RuntimeException("Book not found"))
        );

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
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Stok kontrolü
        if (book.getAvailableCopies() <= 0) {

            // HOLD OLUŞTUR
            HoldDto holdDto = new HoldDto();
            holdDto.setUserId(dto.getUserId());
            holdDto.setBookId(dto.getBookId());

            holdService.createHold(holdDto);

            throw new RuntimeException("Book is not available. You have been added to the hold queue.");
        }

        // Aynı kullanıcı aynı kitabı iade etmeden tekrar alamaz
        boolean alreadyBorrowed = loanRepository.existsByUser_UserIdAndBook_BookIdAndReturnDateIsNull(
                dto.getUserId(), dto.getBookId()
        );
        if (alreadyBorrowed) {
            throw new RuntimeException("You already borrowed this book");
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setIssueDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));
        loan.setReturnDate(null);
        loan.setFineAmount(0.0);

        // stok azalt
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Loan saved = loanRepository.save(loan);
        return loanMapper.toResponseDto(saved);
    }

    public LoanResponseDto returnBook(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getReturnDate() != null) {
            throw new RuntimeException("Book already returned");
        }

        Book book = loan.getBook(); // book burada tanımlandı

        // HOLD SIRASINI KONTROL ET
        List<Hold> holds = holdRepository.findByBook_BookIdAndStatusOrderByHoldDateAsc(
                book.getBookId(), Hold.HoldStatus.PENDING
        );

        if (!holds.isEmpty()) {
            Hold next = holds.get(0);

            next.setStatus(Hold.HoldStatus.COMPLETED);
            holdRepository.save(next);

            // Otomatik Loan
            LoanDto autoLoan = new LoanDto();
            autoLoan.setUserId(next.getUser().getUserId());
            autoLoan.setBookId(book.getBookId());

            borrowBook(autoLoan);
        }

        loan.setReturnDate(LocalDate.now());

        LocalDate dueDate = loan.getIssueDate().plusDays(14);

        if (loan.getReturnDate().isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, loan.getReturnDate());
            loan.setFineAmount(daysLate * 20.0);
        } else {
            loan.setFineAmount(0.0);
        }

        // stok artır
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        Loan saved = loanRepository.save(loan);
        return loanMapper.toResponseDto(saved);
    }

    public List<LoanResponseDto> getActiveLoans(Long userId) {
        List<Loan> loans = loanRepository.findByUser_UserIdAndReturnDateIsNull(userId);
        return loanMapper.toResponseDtoList(loans);
    }
}

