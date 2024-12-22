package com.ra.module5_project.repository;

import com.ra.module5_project.model.entity.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingSeatRepository extends JpaRepository<BookingSeat,Long> {
    @Query("select bs from BookingSeat bs where bs.booking.id = :bookingId")
    List<BookingSeat> findAllByBookingId(@Param("bookingId") long bookingId);

    @Query("delete from BookingSeat bs where bs.booking.id = :bookingId")
    void deleteByBookingId(@Param("bookingId") long bookingId);
}
