package com.lms.backend.library.dto;

import java.time.LocalDate;

public class HoldResponseDto {

    private Long holdId;
    private Long userId;
    private Long bookId;

    private String bookTitle;
    private String bookAuthor;

    private LocalDate holdDate;
    private String status;

    // Getter & Setter
    public Long getHoldId() { return holdId; }
    public void setHoldId(Long holdId) { this.holdId = holdId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public String getBookAuthor() { return bookAuthor; }
    public void setBookAuthor(String bookAuthor) { this.bookAuthor = bookAuthor; }

    public LocalDate getHoldDate() { return holdDate; }
    public void setHoldDate(LocalDate holdDate) { this.holdDate = holdDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
