package com.ra.module5_project.service.booking;

import com.ra.module5_project.model.dto.booking.request.BookingRequest;
import com.ra.module5_project.model.dto.booking.response.BookingPagination;
import com.ra.module5_project.model.entity.Booking;
import com.ra.module5_project.model.entity.Seat;
import com.ra.module5_project.model.entity.ShowTime;
import com.ra.module5_project.repository.BookingRepository;
import com.ra.module5_project.repository.SeatRepository;
import com.ra.module5_project.service.seat.SeatService;
import com.ra.module5_project.service.showTime.ShowTimeService;
import com.ra.module5_project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookingServiceImpl implements BookingService{
    @Autowired
    private BookingRepository bookingRepository ;
    @Autowired
    private ShowTimeService showTimeService ;
    @Autowired
    private UserService userService ;
    @Autowired
    private SeatRepository seatRepository ;
    @Override
    public BookingPagination findAll(Pageable pageable) {
        Page<Booking> page = bookingRepository.findAll(pageable);
        return BookingPagination.builder()
                .bookings(page.getContent())
                .currentPage(page.getNumber())
                .size(page.getSize())
                .totalElement(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();
    }

    @Override
    public Booking save(BookingRequest bookingRequest) {
        List<Seat> seats = bookingRequest.getListSeatId().stream().map(id -> {
            return seatRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found seat"));
        }).toList();
//        double totalPriceMovie = seats.stream().map(seat -> {
//
//        })
        Booking booking = Booking.builder()
                .showTime(showTimeService.findById(bookingRequest.getShowTimeId()))
                .user(userService.findById(bookingRequest.getUserId()))
                .totalSeat(bookingRequest.getListSeatId().size())
                .totalPriceMovie(10)
                .totalPriceFood(10)
                .status(true)
                .created_at(LocalDateTime.now())
                .build();
        return null ;
    }

    @Override
    public Booking findById(long bookingId) {
        return null;
    }
}
