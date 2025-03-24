package com.att.tdp.popcorn_palace.entity;

import jakarta.persistence.* ;
import lombok.Data;

@Data
@Entity
public class Showtime {
    @Id @GeneratedValue
    private Long showtimeId;
    private Long movieId;
    private Double price;
    private String theater;
    private String startTime;
    private String endTime;

}
