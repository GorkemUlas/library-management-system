package com.lms.backend.library.dto;

import lombok.Data;

@Data
public class AdminSummaryDto {
    private long totalUsers;
    private long totalBooks;
    private long totalLoans;
    private long totalHolds;

    private long overdueLoans;
    private long activeLoans;

    private long availableCopies;     // toplam available_copies
    private long outOfStockBooks;     // available_copies = 0
    private long lowStockBooks;       // available_copies < 5


    private String mostBorrowedCategory;

    //getter setterlar

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(long totalBooks) {
        this.totalBooks = totalBooks;
    }

    public long getTotalLoans() {
        return totalLoans;
    }

    public void setTotalLoans(long totalLoans) {
        this.totalLoans = totalLoans;
    }

    public long getTotalHolds() {
        return totalHolds;
    }

    public void setTotalHolds(long totalHolds) {
        this.totalHolds = totalHolds;
    }

    public long getOverdueLoans() {
        return overdueLoans;
    }

    public void setOverdueLoans(long overdueLoans) {
        this.overdueLoans = overdueLoans;
    }

    public long getActiveLoans() {
        return activeLoans;
    }

    public void setActiveLoans(long activeLoans) {
        this.activeLoans = activeLoans;
    }

    public long getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(long availableCopies) {
        this.availableCopies = availableCopies;
    }

    public long getOutOfStockBooks() {
        return outOfStockBooks;
    }

    public void setOutOfStockBooks(long outOfStockBooks) {
        this.outOfStockBooks = outOfStockBooks;
    }

    public long getLowStockBooks() {
        return lowStockBooks;
    }

    public void setLowStockBooks(long lowStockBooks) {
        this.lowStockBooks = lowStockBooks;
    }

    public String getMostBorrowedCategory() {
        return mostBorrowedCategory;
    }

    public void setMostBorrowedCategory(String mostBorrowedCategory) {
        this.mostBorrowedCategory = mostBorrowedCategory;
    }

}

