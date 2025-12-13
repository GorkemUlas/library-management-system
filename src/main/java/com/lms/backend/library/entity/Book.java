package com.lms.backend.library.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

    @Entity
    @Table(name = "Books", schema="dbo")
    public class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "book_id")
        private Long bookId;        private String title;
        private String author;
        private String status; // Available, Borrowed
        private String category;
        @NotNull(message = "Total copies cannot be null")
        private Integer totalCopies;

        @NotNull(message = "Available copies cannot be null")
        private Integer availableCopies;

        //resim
        @Column(name = "image_url")
        private String imageUrl;


        //getter setter


        public Long getBookId() {
            return bookId;
        }

        public void setBookId(Long bookId) {
            this.bookId = bookId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Integer getTotalCopies() {
            return totalCopies;
        }
        public void setTotalCopies(Integer totalCopies) {
            this.totalCopies = totalCopies;
        }
        public Integer getAvailableCopies() {
            return availableCopies;
        }
        public void setAvailableCopies(Integer availableCopies) {
            this.availableCopies = availableCopies;
        }

        public String getImageUrl() {
            return imageUrl;
        }
        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

