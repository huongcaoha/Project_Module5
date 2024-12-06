package com.ra.module5_project.model.dto.screenRoom.request;

import com.ra.module5_project.model.entity.City;
import com.ra.module5_project.model.entity.ScreenRoom;
import com.ra.module5_project.validator.NameExist;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ScreenRoomRequest {
    @NotNull(message = "theater id can not null")
    @Min(1)
    private long theaterId ;

    @NotNull(message = "screen number can not null")
    @Min(1)
    @NameExist(message = "Screen number exist",entityClass = ScreenRoom.class,fieldName = "screenNumber")
    private  int screenNumber ;

    @NotNull(message = "number of seat can not null")
    @Min(1)
    private int numberOfSeats ;

}
