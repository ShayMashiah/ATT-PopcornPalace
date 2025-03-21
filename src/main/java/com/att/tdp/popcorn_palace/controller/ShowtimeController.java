package com.att.tdp.popcorn_palace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.att.tdp.popcorn_palace.DTO.ShowtimeDto;
import com.att.tdp.popcorn_palace.entity.Showtime;
import com.att.tdp.popcorn_palace.service.ShowtimeService;

import lombok.extern.slf4j.Slf4j;

@RestController 
@RequestMapping("/showtimes") 
@Slf4j
public class ShowtimeController {
    @Autowired
    private ShowtimeService showtimeService;

    // Get all showtimes
    @GetMapping("/all")
    public ResponseEntity<Iterable<Showtime>> getAllShowtimes() {
        return ResponseEntity.ok(showtimeService.getAllShowtimes());
    }
    
    // Get a showtime by ID
    @GetMapping("/{showtimeId}")
    public ResponseEntity<Showtime> getShowtime(@PathVariable("showtimeId") Long showtimeId) {
        return ResponseEntity.ok(showtimeService.getShowtime(showtimeId));
    }

    // Add a showtime
    @PostMapping("/")
    public ResponseEntity<Showtime> addShowtime(@RequestBody ShowtimeDto showtimeDto) {
        return ResponseEntity.ok(showtimeService.addShowtime(showtimeDto));
    }

}
