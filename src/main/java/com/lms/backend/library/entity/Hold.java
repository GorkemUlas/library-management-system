package com.lms.backend.library.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Holds", schema = "dbo")
public class Hold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long holdId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDate holdDate;

    public enum HoldStatus {
        PENDING,
        COMPLETED,
        CANCELLED
    }


    @Enumerated(EnumType.STRING)
    private HoldStatus status; // Pending, Approved, Cancelled


    private Integer position;

    // Getter & Setter


    public Long getHoldId() {
        return holdId;
    }
    public void setHoldId(Long holdId) {
        this.holdId = holdId;
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


    public LocalDate getHoldDate() {
        return holdDate;
    }
    public void setHoldDate(LocalDate holdDate) {
        this.holdDate = holdDate;
    }

    public HoldStatus getStatus() {
        return status;
    }

    public void setStatus(HoldStatus status) {
        this.status = status;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
