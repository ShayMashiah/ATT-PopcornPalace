package com.att.tdp.popcorn_palace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.tdp.popcorn_palace.repository.BookingRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    
}
