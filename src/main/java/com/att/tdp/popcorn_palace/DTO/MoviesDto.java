package com.att.tdp.popcorn_palace.DTO;

import jakarta.validation.constraints.*;

public class MoviesDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be greater than 0")
    @Max(value = 300, message = "Duration must be less than 300")
    private Integer duration;

    @NotNull(message = "Rating is required")
    @Min(value = 0, message = "Rating must be greater than 0")
    @Max(value = 10, message = "Rating must be less than 10")
    private Double rating;

    @NotNull(message = "Release year is required")
    @Min(value = 1900, message = "Release year must be greater than 1900")
    @Max(value = 2025, message = "Release year must be less than 2025")
    private Integer releaseYear;

    public MoviesDto(String title, String genre, Integer duration, Double rating, Integer releaseYear) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.rating = rating;
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getDuration() {
        return duration;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
    
}