package com.ra.module5_project.service.movie;

import com.ra.module5_project.exception.CustomException;
import com.ra.module5_project.exception.NoResourceFoundException;
import com.ra.module5_project.exception.ResourceNotFoundException;
import com.ra.module5_project.model.constant.StatusMovie;
import com.ra.module5_project.model.dto.movie.MovieDTO;
import com.ra.module5_project.model.dto.movie.MovieResponse;
import com.ra.module5_project.model.dto.movie.MovieUpdateDTO;
import com.ra.module5_project.model.entity.Category;
import com.ra.module5_project.model.entity.Movie;
import com.ra.module5_project.repository.CategoryRepository;
import com.ra.module5_project.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Page<MovieResponse> findAll(Pageable pageable) {
        // Định dạng ngày tháng năm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Page<MovieResponse> movies = movieRepository.findAll(pageable).map((movie)->{

            return MovieResponse.builder()
                    .id(movie.getId())
                    .movieName(movie.getMovieName())
                    .duration(movie.getDuration())
                    .director(movie.getDirector())
                    .cast(movie.getCast())
                    .poster(movie.getPoster())
                    .language(movie.getLanguage())
                    .description(movie.getDescription())
                    .categoryNames(movie.getCategories().stream()
                            .map(Category::getCategoryName)
                            .collect(Collectors.toList()))
                    .releaseDate(movie.getReleaseDate().format(formatter))
                    .build();
        });
        return movies;
    }

    //Thêm mới
    @Override
    public MovieResponse create(MovieDTO movieDTO) throws CustomException {

        // Tạo mới Movie
        Movie movie = new Movie();
        movie.setMovieName(movieDTO.getMovieName());
        movie.setDuration(movieDTO.getDuration());
        if(movieDTO.getReleaseDate().isBefore(LocalDate.now())) {
            throw new CustomException("Ngày phát hành phải lớn hơn ngày hiện tại");
        }
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setDirector(movieDTO.getDirector());
        movie.setCast(movieDTO.getCast());
        movie.setDescription(movieDTO.getDescription());
        movie.setLanguage(movieDTO.getLanguage());
        movie.setPoster(movieDTO.getPoster());

        // Lấy danh sách các ID thể loại từ MovieDTO
        Set<Long> categoryIds = movieDTO.getCategoryIds();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            Set<Category> categories = new HashSet<>();

            for (Long categoryId : categoryIds) {
                Category category = categoryRepository.findById(categoryId).orElse(null);

                if (category == null) {
                    throw new ResourceNotFoundException("Danh mục không tồn tại.");
                }

                categories.add(category); // Thêm thể loại vào Set
            }

            movie.setCategories(categories); // Cập nhật bộ sưu tập thể loại cho phim
        }

        // Lưu đối tượng movie vào cơ sở dữ liệu
        movie = movieRepository.save(movie);

        // Định dạng ngày tháng năm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Trả về MovieResponse sau khi lưu
        return MovieResponse.builder()
                .id(movie.getId())
                .movieName(movie.getMovieName())
                .duration(movie.getDuration())
                .director(movie.getDirector())
                .cast(movie.getCast())
                .poster(movie.getPoster())
                .language(movie.getLanguage())
                .description(movie.getDescription())
                .categoryNames(movie.getCategories().stream()
                        .map(Category::getCategoryName)
                        .collect(Collectors.toList()))
                .releaseDate(movie.getReleaseDate().format(formatter))
                .build();
    }

    @Override
    public Movie findById(Long id) throws NoResourceFoundException {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) {
            throw new NoResourceFoundException("Bộ phim không tồn tại");
        }
        return movie;
    }

    @Override
    public MovieResponse update(MovieUpdateDTO movieUpdateDTO, Long id) throws CustomException {
        Movie movie = findById(id);
        movie.setId(id);
        // Kiểm tra trùng tên phim trước khi tiếp tục
        boolean check = movieRepository.existsByMovieNameAndId(movieUpdateDTO.getMovieName(), id);
        if (check) {
            throw new ResourceNotFoundException("Tên phim đã tồn tại");
        }

        // Cập nhật các thuộc tính của phim
        movie.setMovieName(movieUpdateDTO.getMovieName());
        movie.setDuration(movieUpdateDTO.getDuration());
        movie.setDirector(movieUpdateDTO.getDirector());
        movie.setCast(movieUpdateDTO.getCast());
        movie.setDescription(movieUpdateDTO.getDescription());
        movie.setLanguage(movieUpdateDTO.getLanguage());
        movie.setReleaseDate(movieUpdateDTO.getReleaseDate());
        movie.setPoster(movieUpdateDTO.getPoster());

        // Lấy danh sách các ID thể loại từ MovieDTO
        Set<Long> categoryIds = movieUpdateDTO.getCategoryIds();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            Set<Category> categories = new HashSet<>();

            for (Long categoryId : categoryIds) {
                Category category = categoryRepository.findById(categoryId).orElse(null);

                if (category == null) {
                    throw new ResourceNotFoundException("Danh mục không tồn tại.");
                }

                categories.add(category); // Thêm thể loại vào Set
            }

            movie.setCategories(categories); // Cập nhật bộ sưu tập thể loại cho phim
        }

        // Lưu lại thông tin phim đã cập nhật
        movie = movieRepository.save(movie);
        // Định dạng ngày tháng năm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Trả về phản hồi sau khi cập nhật
        return MovieResponse.builder()
                .id(movie.getId())
                .movieName(movie.getMovieName())
                .duration(movie.getDuration())
                .director(movie.getDirector())
                .cast(movie.getCast())
                .poster(movie.getPoster())
                .language(movie.getLanguage())
                .description(movie.getDescription())
                .categoryNames(movie.getCategories().stream()
                        .map(Category::getCategoryName)
                        .collect(Collectors.toList()))
                .releaseDate(movie.getReleaseDate().format(formatter))
                .build();
    }


    @Override
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public Page<MovieResponse> search(String keyword, Pageable pageable) {
        Page<Movie> movies = movieRepository.findByMovieNameContaining(keyword, pageable);
        List<MovieResponse> movieResponses = new ArrayList<>();
        // Định dạng ngày tháng năm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Movie movie : movies) {
            MovieResponse movieResponse = MovieResponse.builder()
                    .id(movie.getId())
                    .movieName(movie.getMovieName())
                    .duration(movie.getDuration())
                    .director(movie.getDirector())
                    .cast(movie.getCast())
                    .poster(movie.getPoster())
                    .language(movie.getLanguage())
                    .description(movie.getDescription())
                    .categoryNames(movie.getCategories().stream()
                            .map(Category::getCategoryName)
                            .collect(Collectors.toList()))
                    .releaseDate(movie.getReleaseDate().format(formatter))
                    .build();
            movieResponses.add(movieResponse);
        }
        return new PageImpl<>(movieResponses, pageable, movieResponses.size());
    }
}
