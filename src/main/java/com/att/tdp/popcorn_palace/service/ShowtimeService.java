package com.att.tdp.popcorn_palace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.att.tdp.popcorn_palace.DTO.ShowtimeDto;
import com.att.tdp.popcorn_palace.entity.Booking;
import com.att.tdp.popcorn_palace.entity.Showtime;
import com.att.tdp.popcorn_palace.repository.BookingRepository;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class ShowtimeService {
    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private BookingRepository bookingRepository;

    // Get all showtimes
    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }

    // Get a showtime by ID
    public Showtime getShowtime(Long showtimeId) {
        return showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Showtime not found"));
    }
    
    // Add a showtime only if the movie exists
    public Showtime addShowtime(ShowtimeDto showtimeDto) {
        boolean movieExists = movieRepository.existsById(showtimeDto.getMovieId());
        if (!movieExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
    
        Showtime showtime = new Showtime();
        showtime.setMovieId(showtimeDto.getMovieId());
        showtime.setPrice(showtimeDto.getPrice());
        showtime.setTheater(showtimeDto.getTheater());
        showtime.setStartTime(showtimeDto.getStartTime());
        showtime.setEndTime(showtimeDto.getEndTime());
    
        return showtimeRepository.save(showtime);
    }

    // Update a showtime
    public void updateShowtime(Long showtimeId, ShowtimeDto showtimeDto) {
        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Showtime not found"));

        boolean movieExists = movieRepository.existsById(showtimeDto.getMovieId());
            if (!movieExists) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
            }
            else {
                showtime.setMovieId(showtimeDto.getMovieId());
            }

        showtime.setPrice(showtimeDto.getPrice());
        showtime.setTheater(showtimeDto.getTheater());
        showtime.setStartTime(showtimeDto.getStartTime());
        showtime.setEndTime(showtimeDto.getEndTime());
    
        showtimeRepository.save(showtime);
    }

    // Delete a showtime
    public void deleteShowtime(Long showtimeId) {
        List<Booking> bookings = bookingRepository.findAllByShowtimeId(showtimeId);  
        if (!bookings.isEmpty()) {
            bookingRepository.deleteAllByShowtimeId(showtimeId); 
        }
        showtimeRepository.deleteById(showtimeId);
    }

    // Delete all showtimes
    public void deleteAllShowtimes() {
        bookingRepository.deleteAll();
        showtimeRepository.deleteAll();
    }
    
}
