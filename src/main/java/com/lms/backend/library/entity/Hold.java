package com.lms.backend.library.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Holds", schema = "dbo")
public class Hold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hold_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDate request_date;
    private String status; // Pending, Approved, Cancelled

    // Getter & Setter

    public Long getHold_id() {
        return hold_id;
    }

    public void setHold_id(Long hold_id) {
        this.hold_id = hold_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getRequest_date() {
        return request_date;
    }

    public void setRequest_date(LocalDate request_date) {
        this.request_date = request_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
