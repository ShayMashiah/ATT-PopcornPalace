package com.att.tdp.popcorn_palace.entity;

import jakarta.persistence.* ;

@Entity
public class Showtime {

    @Id @GeneratedValue
    private Long showtimeId;
    private Long movieId;
    private Double price;
    private String theater;
    private String startTime;
    private String endTime;

    public Long getId() {
        return showtimeId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTheater() {
        return theater;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Showtime() {}
    
}
