package com.ra.module5_project.model.dto.showTime.request;

import com.ra.module5_project.model.entity.Movie;
import com.ra.module5_project.model.entity.Theater;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ShowTimeRequest {
    @NotNull(message = "Movie id can not null")
    @Min(1)
    private long movieId ;

    @NotNull(message = "Show time can not null")
    @FutureOrPresent(message = "Show time must be today or in the future")
    private LocalDateTime showTime ;

    @NotNull(message = "Theater id can not null")
    @Min(1)
    private long theaterId ;
}
