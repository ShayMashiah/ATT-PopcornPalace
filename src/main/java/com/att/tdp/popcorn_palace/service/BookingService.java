package com.att.tdp.popcorn_palace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.att.tdp.popcorn_palace.DTO.BookingDto;
import com.att.tdp.popcorn_palace.entity.Booking;
import com.att.tdp.popcorn_palace.entity.Showtime;
import com.att.tdp.popcorn_palace.repository.BookingRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ShowtimeRepository showtimeRepository;

    // Add a new booking
    public Booking addBooking(BookingDto bookingDto) {
        if (bookingDto.getShowtimeId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Showtime ID cannot be null");
        }

        Showtime showtime = showtimeRepository.findById(bookingDto.getShowtimeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Showtime not found"));

        // Check if the seat is already booked for the same showtime
        if (bookingRepository.findBySeatNumberAndShowtimeId(bookingDto.getSeatNumber(), bookingDto.getShowtimeId()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Seat cannot be selected for the same showtime");
    }


        Booking booking = new Booking();
        booking.setShowtimeId(bookingDto.getShowtimeId());
        booking.setSeatNumber(bookingDto.getSeatNumber());
        booking.setUserId(bookingDto.getUserId());
    
        return bookingRepository.save(booking);
    }

    // Get all bookings
    public Iterable<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    
 
}
