package com.att.tdp.popcorn_palace.entity;

import jakarta.persistence.* ;

@Entity
public class Movie {

    @Id @GeneratedValue
    private Long id;
    private String title;
    private String genre;
    private Integer duration;
    private Double rating;
    private Integer releaseYear;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String type) {
        this.genre = type;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Movie() {}
    
}