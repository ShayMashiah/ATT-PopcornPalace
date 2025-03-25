package com.att.tdp.popcorn_palace.apiTests.ShowtimeApiTests;

import com.att.tdp.popcorn_palace.entity.Showtime;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.service.ShowtimeService;
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

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DeleteShowtimeApiTest {

    private static final String DELETE_SHOWTIME_PATH = "/showtimes/{showtimeId}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShowtimeService showtimeService;

    @MockBean
    private ShowtimeRepository showtimeRepository;


    private Showtime existingShowtime;

    @BeforeEach
    void setUp() {
        existingShowtime = new Showtime();
        existingShowtime.setShowtimeId(1L);
        existingShowtime.setMovieId(101L);
        existingShowtime.setTheater("Theater A");
        existingShowtime.setStartTime("2025-04-15 19:00");
        existingShowtime.setEndTime("2025-04-15 21:30");

        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(existingShowtime));
    }

    // Test successful deletion of a showtime
    @Test
    void deleteShowtimeTest() throws Exception {
        doNothing().when(showtimeService).deleteShowtime(1L);

        mockMvc.perform(delete(DELETE_SHOWTIME_PATH, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(showtimeService, times(1)).deleteShowtime(1L);
    }

    // Test deleting non-existent showtime
    @Test
    void deleteShowtimeTest_NonExistentShowtime() throws Exception {
        when(showtimeRepository.findById(999L)).thenReturn(Optional.empty());
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Showtime not found"))
            .when(showtimeService).deleteShowtime(999L);

        mockMvc.perform(delete(DELETE_SHOWTIME_PATH, 999L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // Test delete with invalid path
    @Test
    void deleteShowtimeTest_InvalidPath() throws Exception {
        mockMvc.perform(delete(DELETE_SHOWTIME_PATH, "invalid")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}