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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AddMovieApiTest {

    private static final String ADD_MOVIE_PATH = "/movies/";
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    // Test the add movie API
    @Test
    void addMovieTest() throws Exception {
        MoviesDto movieDto = new MoviesDto("Inception", "Sci-Fi", 148, 8.8, 2010);
        Movie savedMovie = new Movie();
        savedMovie.setId(1L);
        savedMovie.setTitle("Inception");
        savedMovie.setGenre("Sci-Fi");
        savedMovie.setDuration(148);
        savedMovie.setRating(8.8);
        savedMovie.setReleaseYear(2010);

        when(movieService.addMovie(ArgumentMatchers.any(MoviesDto.class))).thenReturn(savedMovie);

        mockMvc.perform(post(ADD_MOVIE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(movieDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.genre").value("Sci-Fi"))
                .andExpect(jsonPath("$.duration").value(148))
                .andExpect(jsonPath("$.rating").value(8.8))
                .andExpect(jsonPath("$.releaseYear").value(2010));
    }

    // Test try to add movie that already exist
    @Test
    void addMovieTest_ExistingMovie() throws Exception {
        MoviesDto existingMovieDto = new MoviesDto("Inception", "Sci-Fi", 150, 9.0, 2010);
    
        when(movieService.addMovie(ArgumentMatchers.any(MoviesDto.class)))
            .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Movie with this title already exists"));
    
        mockMvc.perform(post(ADD_MOVIE_PATH) 
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(existingMovieDto)))
            .andExpect(status().isConflict()); 
    
        verify(movieService, times(1)).addMovie(ArgumentMatchers.any(MoviesDto.class));
    }

    // Test the add movie API with bad request
    @Test
    void addMovieTest_BadRequest() throws Exception {
        MoviesDto invalidMovieDto = new MoviesDto("", "", 350, 15.0, 2030);

        mockMvc.perform(post(ADD_MOVIE_PATH)
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

    // Test the add movie API with invalid path
    @Test
    void addMovieTest_InvalidPath() throws Exception {
        MoviesDto validMovieDto = new MoviesDto("Interstellar", "Sci-Fi", 169, 8.6, 2014);

        mockMvc.perform(post("/invalid-path/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(validMovieDto)))
                .andExpect(status().isNotFound());
    }
}
