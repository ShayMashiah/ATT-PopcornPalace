package com.att.tdp.popcorn_palace.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.att.tdp.popcorn_palace.repository.MovieRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import com.att.tdp.popcorn_palace.DTO.MoviesDto;
import com.att.tdp.popcorn_palace.entity.Movie;

@Service
@Slf4j
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

        // Get all movies
        public List<Movie> getAllMovies() {
            return movieRepository.findAll();
        }

        // Add a movie
        public Movie addMovie(Movie movie) {
            return movieRepository.save(movie);
        }

}

