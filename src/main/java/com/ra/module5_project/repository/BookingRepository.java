package com.ra.module5_project.repository;


import com.ra.module5_project.model.dto.booking.response.BookingPagination;
import com.ra.module5_project.model.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    @Query("select b from Booking  b where b.user.id = :userId")
    List<Booking> findAllByUserId(@Param("userId") long userId);

    @Query("select b from Booking b where (:movieId is null or b.showTime.movie.id = :movieId) " +
            "and (:theaterId is null or b.showTime.theater.id = :theaterId) " +
            "and (:screenRoomId is null or b.showTime.screenRoom.id = :screenRoomId) " +
            "and (:showTimeId is null or b.showTime.id = :showTimeId)")
    Page<Booking> findAllAndSearch(Pageable pageable,
                                   @Param("movieId") Long movieId,
                                   @Param("theaterId") Long theaterId,
                                   @Param("screenRoomId") Long screenRoomId,
                                   @Param("showTimeId") Long showTimeId);
}
