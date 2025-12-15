package com.lms.backend.library.dto;

import java.time.LocalDate;

public class LoanResponseDto {

    private Long loanId;
    //private Long userId;
    private Long bookId;

    private String bookTitle;
    //private String bookAuthor;

    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    private Double fineAmount;

    private String bookImage;
    // Getter & Setter
    public Long getLoanId() { return loanId; }
    public void setLoanId(Long loanId) { this.loanId = loanId; }

//    public Long getUserId() { return userId; }
//    public void setUserId(Long userId) { this.userId = userId; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    //public String getBookAuthor() { return bookAuthor; }
    //public void setBookAuthor(String bookAuthor) { this.bookAuthor = bookAuthor; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public Double getFineAmount() { return fineAmount; }
    public void setFineAmount(Double fineAmount) { this.fineAmount = fineAmount; }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }
}
