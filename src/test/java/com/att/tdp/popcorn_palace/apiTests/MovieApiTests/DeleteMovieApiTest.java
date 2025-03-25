package com.att.tdp.popcorn_palace.apiTests.MovieApiTests;

import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.service.MovieService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class DeleteMovieApiTest {

    private static final String DELETE_MOVIE_PATH = "/movies/";
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private MovieRepository movieRepository;
        

    // Test successful movie deletion
    @Test
    void deleteMovieTest() throws Exception {
        Movie movieToDelete = new Movie();
        movieToDelete.setTitle("Inception");
        movieToDelete.setGenre("Sci-Fi");
        movieToDelete.setDuration(148);
        movieToDelete.setRating(8.8);
        movieToDelete.setReleaseYear(2010);
        doNothing().when(movieService).deleteMovie(movieToDelete.getTitle());

        mockMvc.perform(delete(DELETE_MOVIE_PATH + movieToDelete.getTitle()))
                .andExpect(status().isOk());

        verify(movieService, times(1)).deleteMovie(movieToDelete.getTitle());
    }

    // Test deleting a non-existent movie
    @Test
    void deleteMovieTest_NonExistentMovie() throws Exception {
        String nonExistentMovieTitle= "Focus";
        
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"))
            .when(movieService).deleteMovie(nonExistentMovieTitle);

        mockMvc.perform(delete(DELETE_MOVIE_PATH + nonExistentMovieTitle))
                .andExpect(status().isNotFound());
        
        verify(movieService, times(1)).deleteMovie(nonExistentMovieTitle);
    }

}