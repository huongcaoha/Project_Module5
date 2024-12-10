package com.ra.module5_project.controller.admin;

import com.ra.module5_project.exception.CustomException;
import com.ra.module5_project.model.dto.movie.MovieDTO;
import com.ra.module5_project.model.dto.movie.MovieResponse;
import com.ra.module5_project.model.dto.movie.MovieUpdateDTO;
import com.ra.module5_project.model.entity.Movie;
import com.ra.module5_project.service.movie.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myService.com/v1/admin/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<?> findAllMovies(@PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<MovieResponse> movies = movieService.findAll(pageable);
        return ResponseEntity.ok(movies);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody MovieDTO movieDTO) throws CustomException {
        MovieResponse movieResponse = movieService.create(movieDTO);
        return ResponseEntity.ok(movieResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findMovieById(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody MovieUpdateDTO movieUpdateDTO) throws CustomException {
        MovieResponse movieResponse = movieService.update(movieUpdateDTO, id);
        return ResponseEntity.ok(movieResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> findMovieByTitle(@RequestParam String keyword,@PageableDefault(page = 0, size = 3,sort = "id",direction = Sort.Direction.ASC) Pageable pageable) {
        Page<MovieResponse> movieResponses;
        if(keyword == null || keyword.isEmpty()) {
            movieResponses = movieService.findAll(pageable);
        } else {
            movieResponses = movieService.search(keyword, pageable);
        }
        return ResponseEntity.ok(movieResponses);
    }
}
