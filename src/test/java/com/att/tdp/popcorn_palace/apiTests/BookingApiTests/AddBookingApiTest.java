package com.att.tdp.popcorn_palace.apiTests.BookingApiTests;

import com.att.tdp.popcorn_palace.DTO.BookingDto;
import com.att.tdp.popcorn_palace.entity.Booking;
import com.att.tdp.popcorn_palace.service.BookingService;
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

import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AddBookingApiTest {

    private static final String ADD_BOOKING_PATH = "/bookings/";
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    // Test the add booking API
    @Test
    void addBookingTest() throws Exception {
        BookingDto bookingDto = new BookingDto(1L,3,"123456");
        Booking savedBooking = new Booking();
        savedBooking.setUserId("123456");
        savedBooking.setShowtimeId(1L);
        savedBooking.setSeatNumber(3);

        when(bookingService.addBooking(ArgumentMatchers.any(BookingDto.class))).thenReturn(savedBooking);

        mockMvc.perform(post(ADD_BOOKING_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bookingDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.showtimeId").value(1))
                .andExpect(jsonPath("$.seatNumber").value("3"))
                .andExpect(jsonPath("$.userId").value("123456"));
    }

    // Test try to add booking when seat is already booked
    @Test
    void addBookingTest_ExistingBooking() throws Exception {
       BookingDto existingBookingDto = new BookingDto(1L, 3, "179236");
       
        when(bookingService.addBooking(ArgumentMatchers.any(BookingDto.class)))
           .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Seat cannot be selected for the same showtime"));
       
        mockMvc.perform(post(ADD_BOOKING_PATH) 
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(existingBookingDto)))
                .andExpect(status().isConflict()); 
        
        verify(bookingService, times(1)).addBooking(ArgumentMatchers.any(BookingDto.class));
    }

    // Test the add booking API with bad request
    @Test
    void addBookingTest_BadRequest() throws Exception {
        BookingDto invalidBookingDto = new BookingDto(null, -6, "");

        mockMvc.perform(post(ADD_BOOKING_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invalidBookingDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.showtimeId").value("Showtime ID is required"))
                .andExpect(jsonPath("$.seatNumber").value("Seat number must be greater than 0"))
                .andExpect(jsonPath("$.userId").value("User ID is required"));

    }

    // Test the add booking API with invalid path
    @Test
    void addBookingTest_InvalidPath() throws Exception {
        BookingDto validBookingDto = new BookingDto(1L, 3, "123456");

        mockMvc.perform(post("/invalid-path/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(validBookingDto)))
                .andExpect(status().isNotFound());
    }
}