package com.lms.backend.library.controller;

import com.lms.backend.library.dto.LoanDto;
import com.lms.backend.library.dto.LoanResponseDto;
import com.lms.backend.library.entity.Loan;
import com.lms.backend.library.service.LoanService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanResponseDto> createLoan(@Valid @RequestBody LoanDto dto) {
        return ResponseEntity.ok(loanService.createLoan(dto));
    }

    @GetMapping("/{id}")
    public LoanResponseDto getLoan(@PathVariable Long id) {
        return loanService.getLoan(id);
    }

    @GetMapping
    public List<LoanResponseDto> getAllLoans() {
        return loanService.getAllLoans();
    }

    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }

    @PostMapping("/borrow")
    public ResponseEntity<LoanResponseDto> borrowBook(@Valid @RequestBody LoanDto dto) {
        return ResponseEntity.ok(loanService.borrowBook(dto));
    }

    @PostMapping("/return/{loanId}")
    public ResponseEntity<LoanResponseDto> returnBook(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.returnBook(loanId));
    }

    @GetMapping("/active")
    public List<LoanResponseDto> getActiveLoans(@RequestParam Long userId) {
        return loanService.getActiveLoans(userId);
    }

    @GetMapping("/history")
    public List<LoanResponseDto> getLoanHistory(@RequestParam Long userId) {
        return loanService.getLoanHistory(userId);
    }

}
