package com.att.tdp.popcorn_palace.apiTests.MovieApiTests;

import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.service.MovieService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GetAllMoviesApiTest {

    public static final String PATH = "/movies/all";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    // Set up the mock data
    @BeforeEach
    void setUp() {
        Movie movie1 = new Movie();
        movie1.setTitle("Movie 1");
        movie1.setGenre("Action");
        movie1.setDuration(120);
        movie1.setRating(8.5);
        movie1.setReleaseYear(2020);

        Movie movie2 = new Movie();
        movie2.setTitle("Movie 2");
        movie2.setGenre("Comedy");
        movie2.setDuration(90);
        movie2.setRating(7.2);
        movie2.setReleaseYear(2021);

        List<Movie> movies = Arrays.asList(movie1, movie2);
        when(movieService.getAllMovies()).thenReturn(movies);
    }

    // Test the get all movies API
    @Test
    void testGetAllMovies() throws Exception {
        mockMvc.perform(get(PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Movie 1")))
                .andExpect(jsonPath("$[0].genre", is("Action")))
                .andExpect(jsonPath("$[0].duration", is(120)))
                .andExpect(jsonPath("$[0].rating", is(8.5)))
                .andExpect(jsonPath("$[0].releaseYear", is(2020)))
                .andExpect(jsonPath("$[1].title", is("Movie 2")))
                .andExpect(jsonPath("$[1].genre", is("Comedy")))
                .andExpect(jsonPath("$[1].duration", is(90)))
                .andExpect(jsonPath("$[1].rating", is(7.2)))
                .andExpect(jsonPath("$[1].releaseYear", is(2021)));
    }

    // Test the get all movies API with an empty list
    @Test
    void testGetAllMovies_EmptyList() throws Exception {
        when(movieService.getAllMovies()).thenReturn(List.of());

        mockMvc.perform(get(PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // Test the get all movies API with an incorrect path
    @Test
    void testGetAllMovies_IncorrectPath() throws Exception {
        mockMvc.perform(get("/movies/incorrect_path")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}

 