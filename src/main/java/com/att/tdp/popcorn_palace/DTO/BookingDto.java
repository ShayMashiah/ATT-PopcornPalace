package com.att.tdp.popcorn_palace.DTO;

import jakarta.persistence.criteria.CriteriaBuilder.In;

public class BookingDto {
    public Integer seatNumber;
    public Long userId;

    public BookingDto(Integer seatNumber, Long userId) {
        this.seatNumber = seatNumber;
        this.userId = userId;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
}
