package com.att.tdp.popcorn_palace.controller;


import com.att.tdp.popcorn_palace.DTO.MoviesDto;
import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.service.MovieService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController // This means this class is a Controller`
@RequestMapping("/movies") // This means URL's start with /movies (after Application path)
@Slf4j
public class MovieController {
    @Autowired
    private MovieService movieService;
   
    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }
    
    @PostMapping("/")
    public ResponseEntity<Movie> addMovie(@RequestBody @Valid MoviesDto movieDto) {
        return ResponseEntity.ok(movieService.addMovie(movieDto));
    }

    @PutMapping("update/{movieTitle}")
    public ResponseEntity<Movie> updateMovie(@PathVariable("movieTitle") String movieTitle, @RequestBody @Valid MoviesDto movieDto) {
        movieService.updateMovie(movieTitle, movieDto);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{movieTitle}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable("movieTitle") String movieTitle) {
        movieService.deleteMovie(movieTitle);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/")
    public ResponseEntity<Movie> deleteAllMovies() {
        movieService.deleteAllMovies();
        return ResponseEntity.ok(null);
    }

} 