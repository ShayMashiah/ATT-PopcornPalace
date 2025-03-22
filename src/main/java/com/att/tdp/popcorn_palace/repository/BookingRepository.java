package com.att.tdp.popcorn_palace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.att.tdp.popcorn_palace.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    void deleteAllByShowtimeId(Long showtimeId);
    List<Booking> findAllByShowtimeId(Long showtimeId);
    Booking findBySeatNumber(Integer seatNumber);
}
