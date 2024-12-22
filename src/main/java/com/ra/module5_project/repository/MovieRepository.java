package com.ra.module5_project.repository;

import com.ra.module5_project.model.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findByMovieNameContaining(String keyword, Pageable pageable);
    boolean existsByMovieName(String movieName);

    @Query("select count (m.id) > 0 from Movie m where m.movieName like :movieName and m.id != :id")
    boolean existsByMovieNameAndId(@Param("movieName") String movieName, @Param("id") Long id);

    @Query("select m from Movie m where m.releaseDate < :currentDate ")
    List<Movie> getMovieByMonth(@Param("currentDate")LocalDate currentDate);
}
