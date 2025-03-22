package com.att.tdp.popcorn_palace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.att.tdp.popcorn_palace.entity.Showtime;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    void deleteAllByMovieId(Long movieIds);
    List<Showtime> findAllByMovieId(Long movieId);
    boolean existsByTheaterAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(String theater, String endTime, String startTime);
    boolean existsByTheaterAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndShowtimeIdNot(String theater, String endTime, String startTime, Long showtimeId);
}
