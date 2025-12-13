package com.lms.backend.library.dto;

// Validation importları
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookRequestDto {
/* DTO kullanana entitynin sadece görmesini istediklerimizi göstermek için var controller ve service te parametre olarak entity yerine  */
    // alanlar boş olamaz
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Author cannot be empty")
    private String author;

    @NotBlank(message = "Status cannot be empty")
    private String status;

    @NotBlank(message = "Category cannot be empty")
    private String category;

    @NotNull(message = "Total copies cannot be null")
    private Integer totalCopies;

    private String imageUrl;
    // Getter & Setter
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getTotalCopies() { return totalCopies; }
    public void setTotalCopies(Integer totalCopies) { this.totalCopies = totalCopies; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}