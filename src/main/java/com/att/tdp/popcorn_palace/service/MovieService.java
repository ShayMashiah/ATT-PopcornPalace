package com.att.tdp.popcorn_palace.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.att.tdp.popcorn_palace.repository.BookingRepository;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import com.att.tdp.popcorn_palace.DTO.MoviesDto;
import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.entity.Showtime;

@Service
@Slf4j
@Transactional
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private BookingRepository bookingRepository;

        // Get all movies
        public List<Movie> getAllMovies() {
            return movieRepository.findAll();
        }

        // Add a movie
        public Movie addMovie(MoviesDto movieDto) {
            if(movieRepository.findByTitle(movieDto.getTitle()) != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Movie already exists");
            }

            Movie movie = new Movie();

            movie.setTitle(movieDto.getTitle());
            movie.setGenre(movieDto.getGenre());
            movie.setDuration(movieDto.getDuration());
            movie.setRating(movieDto.getRating());
            movie.setReleaseYear(movieDto.getReleaseYear());
            
            return movieRepository.save(movie);
        }

        // Update a movie
        public void updateMovie(String movieTitle, MoviesDto movieDto) {
            Movie movie = movieRepository.findByTitle(movieTitle);
    
            if(movie == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
            }
    
            if(movieDto.getTitle() != null) {
                movie.setTitle(movieDto.getTitle());
            }
            if(movieDto.getGenre() != null) {
                movie.setGenre(movieDto.getGenre());
            }
            if(movieDto.getDuration() != null) {
                movie.setDuration(movieDto.getDuration());
            }
            if(movieDto.getRating() != null) {
                movie.setRating(movieDto.getRating());
            }
            if(movieDto.getReleaseYear() != null) {
                movie.setReleaseYear(movieDto.getReleaseYear());
            }
            movieRepository.save(movie);
        };

        // Delete a movie
        public void deleteMovie(String movieTitle) {
            Movie movie = movieRepository.findByTitle(movieTitle);

            if(movie == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
            }

            List<Showtime> showtimes = showtimeRepository.findAllByMovieId(movie.getId());
            if (!showtimes.isEmpty()) {
                for (Showtime showtime : showtimes) {
                    bookingRepository.deleteAllByShowtimeId(showtime.getId());
                }
                showtimeRepository.deleteAllByMovieId(movie.getId());
            }
            movieRepository.delete(movie);
        };


        // Delete all movies
        public void deleteAllMovies() {
            bookingRepository.deleteAll();
            showtimeRepository.deleteAll();
            movieRepository.deleteAll();
        }
    

}

