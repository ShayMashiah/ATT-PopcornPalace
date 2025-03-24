package com.att.tdp.popcorn_palace.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
    
}