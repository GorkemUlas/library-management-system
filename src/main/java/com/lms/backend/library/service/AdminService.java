package com.lms.backend.library.service;

import com.lms.backend.library.dto.AdminSummaryDto;
import com.lms.backend.library.entity.Book;
import com.lms.backend.library.entity.Loan;
import com.lms.backend.library.repository.BookRepository;
import com.lms.backend.library.repository.HoldRepository;
import com.lms.backend.library.repository.LoanRepository;
import com.lms.backend.library.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final HoldRepository holdRepository;

    public AdminService(
            UserRepository userRepository,
            BookRepository bookRepository,
            LoanRepository loanRepository,
            HoldRepository holdRepository
    ) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.holdRepository = holdRepository;
    }

    public AdminSummaryDto getSummary() {
        AdminSummaryDto dto = new AdminSummaryDto();

        dto.setTotalUsers(userRepository.count());
        dto.setTotalBooks(bookRepository.count());
        dto.setTotalLoans(loanRepository.count());
        dto.setTotalHolds(holdRepository.count());

        dto.setOverdueLoans(loanRepository.countByStatus(Loan.LoanStatus.OVERDUE));
        dto.setActiveLoans(loanRepository.countByStatus(Loan.LoanStatus.ACTIVE));

        dto.setAvailableCopies(
                Optional.ofNullable(bookRepository.sumAvailableCopies()).orElse(0L)
        );

        dto.setOutOfStockBooks(
                bookRepository.countByAvailableCopies(0)
        );

        dto.setLowStockBooks(
                bookRepository.countByAvailableCopiesLessThan(5)
        );


        List<Object[]> result = loanRepository.findMostBorrowedCategory();
        dto.setMostBorrowedCategory(result.isEmpty() ? "N/A" : (String) result.get(0)[0]);

        return dto;
    }
}