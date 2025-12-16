package com.lms.backend.library.repository;
import com.lms.backend.library.entity.Loan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByUser_UserIdAndBook_BookIdAndReturnDateIsNull(Long userId, Long bookId);
    //List<Loan> findByUser_UserIdAndReturnedTrue(Long userId);
    int countByUser_UserId(Long userId);
    int countByUser_UserIdAndStatus(Long userId, Loan.LoanStatus status);
    List<Loan> findByUser_UserIdAndStatus(Long userId, Loan.LoanStatus status);
    boolean existsByUser_UserIdAndBook_BookIdAndStatus(Long userId, Long bookId, Loan.LoanStatus status);

    List<Loan> findByUser_UserIdAndReturnDateIsNull(Long userId);
}


