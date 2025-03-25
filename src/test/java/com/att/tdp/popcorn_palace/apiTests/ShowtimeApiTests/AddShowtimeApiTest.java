package com.att.tdp.popcorn_palace.apiTests.ShowtimeApiTests;

import com.att.tdp.popcorn_palace.DTO.ShowtimeDto;
import com.att.tdp.popcorn_palace.entity.Showtime;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.service.ShowtimeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AddShowtimeApiTest {

    private static final String ADD_SHOWTIME_PATH = "/showtimes/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShowtimeService showtimeService;

    @MockBean
    private MovieRepository movieRepository;

    private ShowtimeDto validShowtimeDto;
    private Showtime createdShowtime;

    @BeforeEach
    void setUp() {
        validShowtimeDto = new ShowtimeDto(null, null, null, null, null);
        validShowtimeDto.setMovieId(101L);
        validShowtimeDto.setTheater("Theater A");
        validShowtimeDto.setStartTime("2025-04-15 19:00");
        validShowtimeDto.setEndTime("2025-04-15 21:30");
        validShowtimeDto.setPrice(12.50);

        createdShowtime = new Showtime();
        createdShowtime.setShowtimeId(1L);
        createdShowtime.setMovieId(validShowtimeDto.getMovieId());
        createdShowtime.setTheater(validShowtimeDto.getTheater());
        createdShowtime.setStartTime(validShowtimeDto.getStartTime());
        createdShowtime.setEndTime(validShowtimeDto.getEndTime());
        createdShowtime.setPrice(validShowtimeDto.getPrice());

        when(showtimeService.addShowtime(any(ShowtimeDto.class))).thenReturn(createdShowtime);
    }

    // Test successful showtime creation
    @Test
    void addShowtimeTest() throws Exception {
        mockMvc.perform(post(ADD_SHOWTIME_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validShowtimeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.showtimeId", is(1)))
                .andExpect(jsonPath("$.movieId", is(101)))
                .andExpect(jsonPath("$.theater", is("Theater A")))
                .andExpect(jsonPath("$.price", is(12.50)));
    }

    // Test adding showtime with invalid data
    @Test
    void addShowtimeTest_InvalidData() throws Exception {
        ShowtimeDto invalidDto = new ShowtimeDto(null, null, null, null, null);

        mockMvc.perform(post(ADD_SHOWTIME_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // Test adding showtime with null movie ID
    @Test
    void addShowtimeTest_NullMovieId() throws Exception {
        validShowtimeDto.setMovieId(null);

        mockMvc.perform(post(ADD_SHOWTIME_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validShowtimeDto)))
                .andExpect(status().isBadRequest());
    }

    // Test adding showtime with invalid price
    @Test
    void addShowtimeTest_InvalidPrice() throws Exception {
        validShowtimeDto.setPrice(-5.0);

        mockMvc.perform(post(ADD_SHOWTIME_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validShowtimeDto)))
                .andExpect(status().isBadRequest());
    }

    // Test adding showtime with blank time
    @Test
    void addShowtimeTest_BlankTime() throws Exception {
        validShowtimeDto.setStartTime("");

        mockMvc.perform(post(ADD_SHOWTIME_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validShowtimeDto)))
                .andExpect(status().isBadRequest());
    }

    // Test adding showtimes with overlapping times 
    @Test
    void addShowtimeTest_OverlappingShowtimes() throws Exception {
        ShowtimeDto overlappingShowtimeDto1 = new ShowtimeDto(null, null, null, null, null);
        overlappingShowtimeDto1.setMovieId(101L);
        overlappingShowtimeDto1.setTheater("Theater A");
        overlappingShowtimeDto1.setStartTime("2025-04-15 19:00");
        overlappingShowtimeDto1.setEndTime("2025-04-15 21:30");
        overlappingShowtimeDto1.setPrice(12.50);
    
        ShowtimeDto overlappingShowtimeDto2 = new ShowtimeDto(null, null, null, null, null);
        overlappingShowtimeDto2.setMovieId(102L);
        overlappingShowtimeDto2.setTheater("Theater A");
        overlappingShowtimeDto2.setStartTime("2025-04-15 20:00");  
        overlappingShowtimeDto2.setEndTime("2025-04-15 22:00");
        overlappingShowtimeDto2.setPrice(15.00);
    
        Showtime createdShowtime1 = new Showtime();
        when(showtimeService.addShowtime(overlappingShowtimeDto1))
            .thenReturn(createdShowtime1);
    
        when(showtimeService.addShowtime(overlappingShowtimeDto2))
            .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "A showtime already exists in this theater during this time"));
    
        mockMvc.perform(post(ADD_SHOWTIME_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(overlappingShowtimeDto1)))
            .andExpect(status().isOk());
    
        mockMvc.perform(post(ADD_SHOWTIME_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(overlappingShowtimeDto2)))
            .andExpect(status().isBadRequest());
        }

    // Test adding showtimes with not overlapping times
    @Test
    void addShowtimeTest_NonOverlappingShowtimesForSameMovie() throws Exception {
        ShowtimeDto showtimeDto1 = new ShowtimeDto(null, null, null, null, null);
        showtimeDto1.setMovieId(101L);
        showtimeDto1.setTheater("Theater A");
        showtimeDto1.setStartTime("2025-04-15 19:00");
        showtimeDto1.setEndTime("2025-04-15 21:30");
        showtimeDto1.setPrice(12.50);
        
        ShowtimeDto showtimeDto2 = new ShowtimeDto(null, null, null, null, null);
        showtimeDto2.setMovieId(101L);  
        showtimeDto2.setTheater("Theater A");
        showtimeDto2.setStartTime("2025-04-15 22:00"); 
        showtimeDto2.setEndTime("2025-04-15 23:30");
        showtimeDto2.setPrice(15.00);
        
        Showtime createdShowtime1 = new Showtime();
        when(showtimeService.addShowtime(showtimeDto1))
        .thenReturn(createdShowtime1);
        
        Showtime createdShowtime2 = new Showtime();
        when(showtimeService.addShowtime(showtimeDto2))
        .thenReturn(createdShowtime2);
        
         mockMvc.perform(post(ADD_SHOWTIME_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(showtimeDto1)))
        .andExpect(status().isOk());
        
        mockMvc.perform(post(ADD_SHOWTIME_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(showtimeDto2)))
        .andExpect(status().isOk());

    }

    // Test adding showtime with non-existent movie ID
    @Test
    void addShowtimeTest_NonExistentMovie() throws Exception {
        ShowtimeDto showtimeDto = new ShowtimeDto(null, null, null, null, null);
        showtimeDto.setMovieId(999L);  // Non-existent movie ID
        showtimeDto.setTheater("Theater A");
        showtimeDto.setStartTime("2025-04-15 19:00");
        showtimeDto.setEndTime("2025-04-15 21:30");
        showtimeDto.setPrice(12.50);
        
        when(movieRepository.existsById(999L)).thenReturn(false);
        when(showtimeService.addShowtime(showtimeDto))
            .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        
        mockMvc.perform(post(ADD_SHOWTIME_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(showtimeDto)))
        .andExpect(status().isNotFound());
    }

}