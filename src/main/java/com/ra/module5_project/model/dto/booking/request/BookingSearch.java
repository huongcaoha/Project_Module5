package com.ra.module5_project.model.dto.booking.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingSearch {
   private Long movieId ;
   private Long theaterId ;
   private Long screenRoomId ;
   private Long showTimeId ;
}
