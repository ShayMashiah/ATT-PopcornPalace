package com.att.tdp.popcorn_palace.entity;

import jakarta.persistence.* ;
import lombok.Data;

@Data
@Entity
public class Booking {
    @Id @GeneratedValue
    private Long id;
    private Long showtimeId;
    private Integer seatNumber;
    private String userId;
}
