package com.ra.module5_project.service.booking;

import com.ra.module5_project.model.dto.booking.request.BookingRequest;
import com.ra.module5_project.model.dto.booking.response.BookingPagination;
import com.ra.module5_project.model.entity.Booking;
import com.ra.module5_project.security.principle.UserPrinciple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface BookingService {
    BookingPagination findAll(Pageable pageable);
    Booking save(BookingRequest bookingRequest, UserPrinciple userPrinciple);
    Booking findById(long bookingId);
    List<Booking> findAllByUserId(long userId);
}
