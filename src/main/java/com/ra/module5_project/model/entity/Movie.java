package com.ra.module5_project.model.entity;

import com.ra.module5_project.model.constant.StatusMovie;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String movieName;
    private int duration; //Thời lượng phim
    private LocalDate releaseDate; //Ngày phát hành
    private String director; //Đạo diễn
    private String cast; //Diễn viên chính
    private String description;
    private String language;
    private String poster;
    private String trailer;

    @ManyToMany
    @JoinTable(
            name = "movie_"
    )

    @Enumerated(EnumType.STRING)
    private StatusMovie statusMovie;
}
