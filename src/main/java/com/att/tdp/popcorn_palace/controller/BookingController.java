package com.att.tdp.popcorn_palace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.popcorn_palace.DTO.BookingDto;
import com.att.tdp.popcorn_palace.entity.Booking;
import com.att.tdp.popcorn_palace.service.BookingService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController 
@RequestMapping("/bookings")
@Slf4j
public class BookingController {
    @Autowired
    private BookingService bookingService;

    // Add a new booking
    @PostMapping("/")
    public ResponseEntity<Booking> addBooking(@RequestBody @Valid BookingDto bookingDto) {
        return ResponseEntity.ok(bookingService.addBooking(bookingDto));
    }

    // Get all bookings
    @GetMapping("/")
    public ResponseEntity<Iterable<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
}
