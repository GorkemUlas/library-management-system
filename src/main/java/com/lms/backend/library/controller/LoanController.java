package com.lms.backend.library.controller;

import com.lms.backend.library.dto.LoanDto;
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

    /*@PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        return loanService.addLoan(loan);
    }*/

    @PostMapping
    public ResponseEntity<Loan> createLoan(@Valid @RequestBody LoanDto dto) {
        Loan created = loanService.createLoan(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public Loan getLoan(@PathVariable Long id) {
        return loanService.getLoan(id);
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }
}
