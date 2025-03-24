package com.att.tdp.popcorn_palace.apiTests.MovieApiTests;

import com.att.tdp.popcorn_palace.entity.Booking;
import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.entity.Showtime;
import com.att.tdp.popcorn_palace.repository.BookingRepository;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.service.BookingService;
import com.att.tdp.popcorn_palace.service.MovieService;
import com.att.tdp.popcorn_palace.service.ShowtimeService;

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

import java.util.Arrays;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class DeleteMovieApiTest {

    private static final String DELETE_MOVIE_PATH = "/movies/";
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private ShowtimeService showtimeService;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private MovieRepository movieRepository;
    
    @MockBean
    private ShowtimeRepository showtimeRepository;
    
    @MockBean
    private BookingRepository bookingRepository;
    

    // Test successful movie deletion
    @Test
    void testDeleteMovie() throws Exception {
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

    // // Test deleting a movie with showtimes and bookings
    // @Test
    // void testDeleteMovie_CascadesDeletesShowtimesAndBookings() throws Exception {
    //     // Setup test data
    //     String movieTitle = "Inception";
        
    //     Movie movie = new Movie();
    //     movie.setId(1L);
    //     movie.setTitle(movieTitle);
    //     movie.setGenre("Sci-Fi");
    //     movie.setDuration(148);
    //     movie.setRating(8.8);
    //     movie.setReleaseYear(2010);
    
    //     movieRepository.save(movie);
        
    //     Showtime showtime1 = new Showtime();
    //     showtime1.setShowtimeId(101L);
    //     showtime1.setMovieId(movie.getId());
    //     showtime1.setPrice(20.0);
    //     showtime1.setTheater("Theater 1");
    //     showtime1.setStartTime("2021-12-01T12:00:00");
    //     showtime1.setEndTime("2021-12-01T15:00:00");
        
    //     Showtime showtime2 = new Showtime();
    //     showtime2.setShowtimeId(102L);
    //     showtime2.setMovieId(movie.getId());
    //     showtime2.setPrice(20.0);
    //     showtime2.setTheater("Theater 2");
    //     showtime2.setStartTime("2021-12-01T12:00:00");
    //     showtime2.setEndTime("2021-12-01T15:00:00");
    
    //     showtimeRepository.save(showtime1);
    //     showtimeRepository.save(showtime2);
    
    //     Booking booking1 = new Booking();
    //     booking1.setId(1001L);
    //     booking1.setShowtimeId(showtime1.getShowtimeId());
    //     booking1.setSeatNumber(2);
    //     booking1.setUserId("1");
    
    //     Booking booking2 = new Booking();
    //     booking2.setId(1002L);
    //     booking2.setShowtimeId(showtime1.getShowtimeId());
    //     booking2.setSeatNumber(3);
    //     booking2.setUserId("1");
    
    //     Booking booking3 = new Booking();
    //     booking3.setId(1003L);
    //     booking3.setShowtimeId(showtime2.getShowtimeId());
    //     booking3.setSeatNumber(3);
    //     booking3.setUserId("1");
    
    //     bookingRepository.save(booking1);
    //     bookingRepository.save(booking2);
    //     bookingRepository.save(booking3);
    
    //     List<Booking> bookings1 = Arrays.asList(booking1, booking2);
    //     List<Booking> bookings2 = Arrays.asList(booking3);
    //     List<Showtime> showtimes = Arrays.asList(showtime1, showtime2);
        
    //     // Mock repository behavior
    //     when(movieRepository.findByTitle(movieTitle)).thenReturn(movie);
    //     when(showtimeRepository.findAllByMovieId(movie.getId())).thenReturn(showtimes);
    //     when(bookingRepository.findAllByShowtimeId(showtime1.getShowtimeId())).thenReturn(bookings1);
    //     when(bookingRepository.findAllByShowtimeId(showtime2.getShowtimeId())).thenReturn(bookings2);
        
    //     // Execute the API call
    //     mockMvc.perform(delete(DELETE_MOVIE_PATH + movieTitle))
    //            .andExpect(status().isOk());
        
    //     // Verify the deletion chain occurred correctly
    //     verify(bookingRepository).deleteAllByShowtimeId(showtime1.getShowtimeId());
    //     verify(bookingRepository).deleteAllByShowtimeId(showtime2.getShowtimeId());
    //     verify(showtimeRepository).delete(showtime1);
    //     verify(showtimeRepository).delete(showtime2);
    //     verify(movieRepository).delete(movie);
    // }
    
    

    // Test deleting a non-existent movie
    @Test
    void testDeleteMovie_NonExistentMovie() throws Exception {
        String nonExistentMovieTitle= "Focus";
        
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"))
            .when(movieService).deleteMovie(nonExistentMovieTitle);

        mockMvc.perform(delete(DELETE_MOVIE_PATH + nonExistentMovieTitle))
                .andExpect(status().isNotFound());
        
        verify(movieService, times(1)).deleteMovie(nonExistentMovieTitle);
    }

}