package com.ra.module5_project.model.dto.movie;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MovieResponse {
    private Long id;
    private String movieName;
    private int duration; //Thời lượng phim
    private String releaseDate; //Ngày phát hành
    private String director; //Đạo diễn
    private String cast; //Diễn viên chính
    private String description;
    private String language;
    private String poster;
    private List<String> categoryNames;
}
