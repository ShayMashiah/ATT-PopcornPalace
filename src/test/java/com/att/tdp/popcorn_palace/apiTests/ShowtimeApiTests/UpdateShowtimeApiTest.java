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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UpdateShowtimeApiTest {

    private static final String UPDATE_SHOWTIME_PATH = "/showtimes/update/{showtimeId}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShowtimeService showtimeService;

    @MockBean
    private MovieRepository movieRepository;

    private ShowtimeDto validShowtimeDto;
    private Showtime updatedShowtime;

    @BeforeEach
    void setUp() {
        validShowtimeDto = new ShowtimeDto(null, null, null, null, null);
        validShowtimeDto.setMovieId(101L);
        validShowtimeDto.setTheater("Theater A");
        validShowtimeDto.setStartTime("2025-04-15 19:00");
        validShowtimeDto.setEndTime("2025-04-15 21:30");
        validShowtimeDto.setPrice(12.50);

        updatedShowtime = new Showtime();
        updatedShowtime.setShowtimeId(1L);
        updatedShowtime.setMovieId(validShowtimeDto.getMovieId());
        updatedShowtime.setTheater(validShowtimeDto.getTheater());
        updatedShowtime.setStartTime(validShowtimeDto.getStartTime());
        updatedShowtime.setEndTime(validShowtimeDto.getEndTime());
        updatedShowtime.setPrice(validShowtimeDto.getPrice());

        when(showtimeService.updateShowtime(eq(1L), any(ShowtimeDto.class))).thenReturn(updatedShowtime);
    }

    // Test successful showtime update
    @Test
    void updateShowtimeTest() throws Exception {
        long showtimeId = 1L;
        
        mockMvc.perform(put(UPDATE_SHOWTIME_PATH, showtimeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validShowtimeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.showtimeId", is(1)))
                .andExpect(jsonPath("$.movieId", is(101)))
                .andExpect(jsonPath("$.theater", is("Theater A")))
                .andExpect(jsonPath("$.price", is(12.50)));
    }

    // Test updating showtime with invalid data
    @Test
    void updateShowtimeTest_InvalidData() throws Exception {
        ShowtimeDto invalidDto = new ShowtimeDto(null, null, null, null, null);

        mockMvc.perform(put(UPDATE_SHOWTIME_PATH, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // Test updating showtime with null movie ID
    @Test
    void updateShowtimeTest_NullMovieId() throws Exception {
        validShowtimeDto.setMovieId(null);

        mockMvc.perform(put(UPDATE_SHOWTIME_PATH, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validShowtimeDto)))
                .andExpect(status().isBadRequest());
    }

    // Test updating showtime with invalid price
    @Test
    void updateShowtimeTest_InvalidPrice() throws Exception {
        validShowtimeDto.setPrice(-5.0);

        mockMvc.perform(put(UPDATE_SHOWTIME_PATH, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validShowtimeDto)))
                .andExpect(status().isBadRequest());
    }

    // Test updating showtime with blank time
    @Test
    void updateShowtimeTest_BlankTime() throws Exception {
        validShowtimeDto.setStartTime("");

        mockMvc.perform(put(UPDATE_SHOWTIME_PATH, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validShowtimeDto)))
                .andExpect(status().isBadRequest());
    }

    // Test updating showtime with non-existent showtime ID
    @Test
    void updateShowtimeTest_NonExistentShowtime() throws Exception {
        long nonExistentShowtimeId = 999L;

        when(showtimeService.updateShowtime(eq(nonExistentShowtimeId), any(ShowtimeDto.class)))
            .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Showtime not found"));

        mockMvc.perform(put(UPDATE_SHOWTIME_PATH, nonExistentShowtimeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validShowtimeDto)))
                .andExpect(status().isNotFound());
    }

    // Test updating showtime with overlapping times (same movie and theater)
    @Test
    void updateShowtimeTest_OverlappingShowtimes() throws Exception {
        ShowtimeDto overlappingShowtimeDto = new ShowtimeDto(null, null, null, null, null);
        overlappingShowtimeDto.setMovieId(101L);
        overlappingShowtimeDto.setTheater("Theater A");
        overlappingShowtimeDto.setStartTime("2025-04-15 19:00");
        overlappingShowtimeDto.setEndTime("2025-04-15 21:30");
        overlappingShowtimeDto.setPrice(12.50);

        when(showtimeService.updateShowtime(eq(1L), any(ShowtimeDto.class)))
            .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        mockMvc.perform(put(UPDATE_SHOWTIME_PATH, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(overlappingShowtimeDto)))
                .andExpect(status().isBadRequest());
    }
}
