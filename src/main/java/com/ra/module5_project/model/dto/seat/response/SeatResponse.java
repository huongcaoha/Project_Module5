package com.ra.module5_project.model.dto.seat.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SeatResponse {
    private long id ;

    private String screenName ;

    private String seatName ;

    private boolean status ;
}
