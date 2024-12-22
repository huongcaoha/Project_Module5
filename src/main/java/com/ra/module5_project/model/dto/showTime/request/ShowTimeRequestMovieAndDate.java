package com.ra.module5_project.model.dto.showTime.request;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShowTimeRequestMovieAndDate {
    private long movieId ;
    private LocalDate date ;

}
