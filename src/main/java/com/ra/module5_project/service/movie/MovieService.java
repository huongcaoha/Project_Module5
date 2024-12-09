package com.ra.module5_project.service.movie;

import com.ra.module5_project.exception.CustomException;
import com.ra.module5_project.model.dto.movie.MovieDTO;
import com.ra.module5_project.model.dto.movie.MovieResponse;
import com.ra.module5_project.model.dto.movie.MovieUpdateDTO;
import com.ra.module5_project.model.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Page<MovieResponse> findAll(Pageable pageable);
    MovieResponse create(MovieDTO movieDTO) throws CustomException;
    Movie findById(Long id);
    MovieResponse update(MovieUpdateDTO movieUpdateDTO, Long id) throws CustomException;
    void delete(Long id);
    Page<MovieResponse> search(String keyword, Pageable pageable);
}
