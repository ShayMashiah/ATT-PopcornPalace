package com.att.tdp.popcorn_palace.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingDto {
    @NotNull(message = "Showtime ID is required")
    public Long showtimeId;

    @NotNull(message = "Seat number is required")
    @Positive(message = "Seat number must be greater than 0")
    public Integer seatNumber;

    @NotBlank(message = "User ID is required")
    public String userId;
    
}
