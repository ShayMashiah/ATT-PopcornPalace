package com.att.tdp.popcorn_palace.DTO;

public class BookingDto {
    public Long showtimeId;
    public Integer seatNumber;
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
