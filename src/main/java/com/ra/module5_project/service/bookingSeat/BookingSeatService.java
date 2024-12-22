package com.ra.module5_project.service.bookingSeat;

import com.ra.module5_project.model.dto.bookingSeat.response.BookingSeatPagination;
import com.ra.module5_project.model.entity.Booking;
import com.ra.module5_project.model.entity.BookingSeat;

import java.util.List;

public interface BookingSeatService {
    List<BookingSeat> findAllByBookingId(long bookingId);
    BookingSeat save(BookingSeat bookingSeat);
    BookingSeat findById(long id) ;

    void deleteByBookingId(long bookingId);
}
