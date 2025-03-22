package com.att.tdp.popcorn_palace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.att.tdp.popcorn_palace.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
