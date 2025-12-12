package com.lms.backend.library.service;

import com.lms.backend.library.mapper.LoanMapper;
import com.lms.backend.library.repository.BookRepository;
import com.lms.backend.library.repository.UserRepository;
import com.lms.backend.library.dto.LoanDto;
import com.lms.backend.library.entity.Book;
import com.lms.backend.library.entity.User;
import com.lms.backend.library.entity.Loan;
import com.lms.backend.library.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final LoanMapper loanMapper;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.loanMapper = loanMapper;
    }

    public Loan createLoan(LoanDto dto) {

        // DTO → Entity (user/book hariç)
        Loan loan = loanMapper.toEntity(dto);

        // User ve Book'u DB'den çekip set ediyoruz
        loan.setUser(
                userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );

        loan.setBook(
                bookRepository.findById(dto.getBookId())
                        .orElseThrow(() -> new RuntimeException("Book not found"))
        );

        return loanRepository.save(loan);
    }

    /*public Loan addLoan(Loan loan) {
        return loanRepository.save(loan);
    }*/

    /*public Loan createLoan(LoanDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);

        return loanRepository.save(loan);
    }*/

    public Loan getLoan(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }
}