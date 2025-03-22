package com.att.tdp.popcorn_palace.DTO;

import jakarta.validation.constraints.*;

public class BookingDto {
    @NotNull(message = "Showtime ID is required")
    public Long showtimeId;

    @NotNull(message = "Seat number is required")
    @Positive(message = "Seat number must be greater than 0")
    public Integer seatNumber;

    @NotBlank(message = "User ID is required")
    public String userId;

    public BookingDto(Long showtimeId, Integer seatNumber, String userId) {
        this.showtimeId = showtimeId;
        this.seatNumber = seatNumber;
        this.userId = userId;
    }

    public Long getShowtimeId() {
        return showtimeId;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}
