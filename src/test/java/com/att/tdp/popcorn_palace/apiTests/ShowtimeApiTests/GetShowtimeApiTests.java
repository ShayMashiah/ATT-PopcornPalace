package com.att.tdp.popcorn_palace.apiTests.ShowtimeApiTests;

import com.att.tdp.popcorn_palace.entity.Showtime;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GetShowtimeApiTests {

    public static final String ALL_SHOWTIMES_PATH = "/showtimes/all";
    public static final String SINGLE_SHOWTIME_PATH = "/showtimes/1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShowtimeService showtimeService;

    private Showtime showtime1;
    private Showtime showtime2;

    @BeforeEach
    void setUp() {
        showtime1 = new Showtime();
        showtime1.setShowtimeId(1L);
        showtime1.setMovieId(101L);
        showtime1.setTheater("Theater A");
        showtime1.setStartTime("15:00");
        showtime1.setEndTime("16:00");
        showtime1.setPrice(12.50);

        showtime2 = new Showtime();
        showtime2.setShowtimeId(2L);
        showtime2.setMovieId(102L);
        showtime2.setTheater("Theater B");
        showtime2.setStartTime("15:00");
        showtime2.setEndTime("16:00");
        showtime2.setPrice(13.50);

        List<Showtime> showtimes = Arrays.asList(showtime1, showtime2);
        when(showtimeService.getAllShowtimes()).thenReturn(showtimes);
        when(showtimeService.getShowtime(1L)).thenReturn(showtime1);
    }

    // Test get all showtimes
    @Test
    void getAllShowtimesTest() throws Exception {
        mockMvc.perform(get(ALL_SHOWTIMES_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].showtimeId", containsInAnyOrder(1,2)))
                .andExpect(jsonPath("$[*].movieId", containsInAnyOrder(101,102)))
                .andExpect(jsonPath("$[*].theater", containsInAnyOrder("Theater A","Theater B")))
                .andExpect(jsonPath("$[*].price", containsInAnyOrder(12.50,13.50)));

    }

    // Test get single showtime by ID
    @Test
    void getShowtimeByIdTest() throws Exception {
        mockMvc.perform(get(SINGLE_SHOWTIME_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.showtimeId", is(1)))
                .andExpect(jsonPath("$.movieId", is(101)))
                .andExpect(jsonPath("$.theater", is("Theater A")))
                .andExpect(jsonPath("$.price", is(12.50)));
    }

    // Test get all showtimes with empty list
    @Test
    void getAllShowtimesTest_EmptyList() throws Exception {
        when(showtimeService.getAllShowtimes()).thenReturn(List.of());

        mockMvc.perform(get(ALL_SHOWTIMES_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // Test get showtime with incorrect path
    @Test
    void getShowtimeTest_InvalidPath() throws Exception {
        mockMvc.perform(get("/showtimes/invalid_path")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    // Test get non-existent showtime
    @Test
    void getShowtimeTest_NotFound() throws Exception {
        when(showtimeService.getShowtime(999L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "showtime not found"));
        mockMvc.perform(get("/showtimes/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
