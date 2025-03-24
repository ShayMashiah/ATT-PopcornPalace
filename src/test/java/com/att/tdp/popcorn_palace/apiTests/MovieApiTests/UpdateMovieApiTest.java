package com.att.tdp.popcorn_palace.apiTests.MovieApiTests;

import com.att.tdp.popcorn_palace.DTO.MoviesDto;
import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UpdateMovieApiTest {

    private static final String UPDATE_MOVIE_PATH = "/movies/update/{movieTitle}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    // Test the update movie API
    @Test
    void testUpdateMovie() throws Exception {
        String movieTitle = "Inception";
    
        Movie updatedMovie = new Movie();
        updatedMovie.setId(1L);
        updatedMovie.setTitle("Inception");
        updatedMovie.setGenre("Sci-Fi");
        updatedMovie.setDuration(150); 
        updatedMovie.setRating(9.0);
        updatedMovie.setReleaseYear(2010);
    
        MoviesDto updatedMovieDto = new MoviesDto("Inception", "Sci-Fi", 150, 9.0, 2010);
        
        when(movieService.updateMovie(eq(movieTitle), ArgumentMatchers.any(MoviesDto.class))).thenReturn(updatedMovie);
    
        mockMvc.perform(put(UPDATE_MOVIE_PATH, movieTitle)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedMovieDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.genre").value("Sci-Fi"))
                .andExpect(jsonPath("$.duration").value(150))
                .andExpect(jsonPath("$.rating").value(9.0))
                .andExpect(jsonPath("$.releaseYear").value(2010));
    
        verify(movieService, times(1)).updateMovie(eq(movieTitle), ArgumentMatchers.any(MoviesDto.class));
    }

    // Test the update movie API with not existing movie
    @Test
    void testUpdateMovie_NotFound() throws Exception {
        String movieTitle = "NonExistentMovie";
        MoviesDto updatedMovieDto = new MoviesDto("NonExistentMovie", "Drama", 120, 7.5, 2015);
    
        when(movieService.updateMovie(eq(movieTitle), ArgumentMatchers.any(MoviesDto.class)))
            .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
    
        mockMvc.perform(put(UPDATE_MOVIE_PATH, movieTitle)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedMovieDto)))
                .andExpect(status().isNotFound());
    }

    // Test the update movie API with invalid data
    @Test
    void testUpdateMovie_WithInvalidParameters() throws Exception {
        String movieTitle = "Inception";
        MoviesDto invalidMovieDto = new MoviesDto("", "", 400, 11.5, 3000);
    
        mockMvc.perform(put(UPDATE_MOVIE_PATH, movieTitle)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(invalidMovieDto)))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.duration").value("Duration must be less than 300"))
            .andExpect(jsonPath("$.rating").value("Rating must be less than 10"))
            .andExpect(jsonPath("$.genre").value("Genre is required"))
            .andExpect(jsonPath("$.title").value("Title is required"))
            .andExpect(jsonPath("$.releaseYear").value("Release year must be less than 2025"));
        }
    
    // Test the update movie API with invalid path
    @Test
    void testUpdateMovie_WithInvalidPath() throws Exception {
        MoviesDto validMovieDto = new MoviesDto("Inception", "Sci-Fi", 140, 9.0, 2010);
    
        String invalidPath = "/update/Inceptio";
    
        mockMvc.perform(put(invalidPath)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(validMovieDto)))
            .andExpect(status().isNotFound()); 
    }
}
