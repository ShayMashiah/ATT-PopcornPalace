package com.att.tdp.popcorn_palace.entity;

import jakarta.persistence.* ;
import lombok.Data;

@Data
@Entity
public class Movie {

    @Id @GeneratedValue
    private Long id;
    private String title;
    private String genre;
    private Integer duration;
    private Double rating;
    private Integer releaseYear;
    
}