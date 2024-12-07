package com.ra.module5_project.service.seat;

import com.ra.module5_project.model.dto.seat.request.SeatRequest;
import com.ra.module5_project.model.dto.seat.response.SeatPagination;
import com.ra.module5_project.model.dto.seat.response.SeatResponse;
import org.springframework.data.domain.Pageable;

public interface SeatService {
    SeatPagination findAllAndSearch(Pageable pageable , String search);
    SeatResponse save(SeatRequest seatRequest);
    SeatResponse update(long id ,SeatRequest seatRequest);
    void deleteById(long id);
    SeatResponse findById(long id);
    void autoCreateSeat(long screenRoomId,int numberSeat);
    void autoDeleteSeat(long screenRoomId);
}
