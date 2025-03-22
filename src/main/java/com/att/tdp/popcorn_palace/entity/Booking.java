package com.att.tdp.popcorn_palace.entity;

import jakarta.persistence.* ;

@Entity
public class Booking {
    @Id @GeneratedValue
    private Long id;
    private Long showtimeId;
    private Integer seatNumber;
    private Long userId;

    public Long getId() {
        return id;
    }

    public Long getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Long showtimeId) {
        this.showtimeId = showtimeId;
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

    public Booking() {}
    
}
