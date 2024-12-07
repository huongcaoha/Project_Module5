package com.ra.module5_project.repository;

import com.ra.module5_project.model.entity.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    @Query("select s from Seat s where s.seatName like %:search%")
    Page<Seat> findAllAndSearch(Pageable pageable , @Param("search") String search);

    @Query("select count(s) > 0 from Seat s where s.seatName = :seatName and s.screenRoom.id = :screenRoomId")
    boolean checkSeatNameExist(@Param("seatName") String seatName , @Param("screenRoomId") long screenRoomId);


}
