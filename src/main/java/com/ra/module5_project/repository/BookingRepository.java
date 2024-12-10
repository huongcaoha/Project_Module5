package com.ra.module5_project.repository;


import com.ra.module5_project.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    @Query("select b from Booking  b where b.user.id = :userId")
    List<Booking> findAllByUserId(@Param("userId") long userId);
}
