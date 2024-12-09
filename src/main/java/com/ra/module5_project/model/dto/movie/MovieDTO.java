package com.ra.module5_project.model.dto.movie;

import com.ra.module5_project.validator.UniqueMovieName;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieDTO {
    @NotBlank(message = "Tên phim không được để trống!")
    @UniqueMovieName(message = "Tên phim đã tồn tại")
    private String movieName;

    @Min(value = 1,message = "Thời lượng phim lớn hơn 0")
    private int duration; //Thời lượng phim

    @NotNull(message = "Ngày phát hành không được để trống")
    private LocalDate releaseDate; //Ngày phát hành

    @NotBlank(message = "Đạo diễn không được để trống")
    private String director; //Đạo diễn

    @NotBlank(message = "Diễn viên chính không được để trống")
    private String cast; //Diễn viên chính

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    @NotBlank(message = "Ngôn ngữ không được để trống")
    private String language;

    @NotBlank(message = "Poster không được để trống")
    private String poster;

    @NotBlank(message = "Trailer không được để trống")
    private String trailer;

    @Min(value = 1,message = "Mã thể loại không được để trống")
    private Long categoryId;
}
