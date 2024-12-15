package com.ra.module5_project.service.booking;

import com.ra.module5_project.model.constant.TypeSeat;
import com.ra.module5_project.model.dto.booking.request.BookingRequest;
import com.ra.module5_project.model.dto.booking.request.BookingSearch;
import com.ra.module5_project.model.dto.booking.response.BookingPagination;
import com.ra.module5_project.model.entity.*;
import com.ra.module5_project.repository.BookingRepository;
import com.ra.module5_project.repository.BookingSeatRepository;
import com.ra.module5_project.repository.SeatRepository;
import com.ra.module5_project.repository.TicketPriceRepository;
import com.ra.module5_project.security.principle.UserPrinciple;
import com.ra.module5_project.service.seat.SeatService;
import com.ra.module5_project.service.showTime.ShowTimeService;
import com.ra.module5_project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
    @Autowired
    private TicketPriceRepository ticketPriceRepository ;
    @Autowired
    private BookingSeatRepository bookingSeatRepository ;
    @Override
    public BookingPagination findAll(Pageable pageable , BookingSearch search) {
        Page<Booking> page ;
        if(search != null){
            page = bookingRepository.findAllAndSearch(pageable, search.getMovieId(), search.getTheaterId(), search.getScreenRoomId(), search.getShowTimeId());
        }
        else {
            page = bookingRepository.findAll(pageable);
        }
        return BookingPagination.builder()
                .bookings(page.getContent())
                .currentPage(page.getNumber())
                .size(page.getSize())
                .totalElement(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();
    }

    @Override
    public Booking save(BookingRequest bookingRequest , @AuthenticationPrincipal UserPrinciple userPrinciple) {

        double totalPriceMovie = getPriceOfSeat(showTimeService.findById(bookingRequest.getShowTimeId()),bookingRequest);
        Booking booking = Booking.builder()
                .showTime(showTimeService.findById(bookingRequest.getShowTimeId()))
                .user(userPrinciple.getUser())
                .totalSeat(bookingRequest.getListSeatId().size())
                .totalPriceMovie(totalPriceMovie)
                .totalPriceFood(bookingRequest.getTotalPriceFood())
                .status(true)
                .created_at(LocalDateTime.now())
                .build();
        Booking newBooking = bookingRepository.save(booking) ;
        List<Seat> seats = bookingRequest.getListSeatId().stream().map(id -> {
            return seatRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found seat"));
        }).toList();
        seats.forEach(seat -> {
            BookingSeat bookingSeat = BookingSeat.builder()
                    .booking(newBooking)
                    .seat(seat)
                    .quantity(seat.getTypeSeat() == TypeSeat.STANDARD ? 1 : 2)
                    .price(convertSeatToPrice(seat,showTimeService.findById(bookingRequest.getShowTimeId())))
                    .created_date(LocalDateTime.now())
                    .build();

            bookingSeatRepository.save(bookingSeat);
        });
        return newBooking ;
    }

    double convertSeatToPrice(Seat seat,ShowTime showTime){
        LocalTime startTime = null ;
        LocalTime endTime  = null ;
        if(LocalTime.now().isAfter(LocalTime.of(10,0)) && LocalTime.now().isBefore(LocalTime.of(22,0))){
            startTime = LocalTime.of(10,0,0);
            endTime = LocalTime.of(22,0,0);
        }else {
            startTime = LocalTime.of(22,0,1);
            endTime = LocalTime.of(9,59 ,59);
        }
        LocalTime finalStartTime = startTime;
        LocalTime finalEndTime = endTime;
        int dayOfWeek = 0 ;
        if(LocalDate.now().getDayOfWeek().getValue() > 5 && LocalDate.now().getDayOfWeek().getValue() <= 7){
            dayOfWeek = 78 ;
        }else {
            dayOfWeek = 23456 ;
        }
        int finalDayOfWeek = dayOfWeek;
        List<Holiday> holidays = new ArrayList<>();
        holidays.add(Holiday.builder().month(1).day(1).build());
        holidays.add(Holiday.builder().month(4).day(30).build());
        holidays.add(Holiday.builder().month(5).day(1).build());
        holidays.add(Holiday.builder().month(9).day(2).build());
        holidays.add(Holiday.builder().month(12).day(24).build());
        boolean isHoliday = holidays.contains(Holiday.builder().month(LocalDate.now().getMonthValue()).day(LocalDate.now().getDayOfMonth()).build());
        TicketPrice ticketPrice = TicketPrice.builder()
                .typeSeat(seat.getTypeSeat().toString().toLowerCase())
                .typeMovie(showTime.getTypeMovie().toString())
                .startTime(finalStartTime)
                .endTime(finalEndTime)
                .dayOfWeeks(finalDayOfWeek)
                .isHoliday(isHoliday)
                .price(0)
                .build();
        TicketPrice dataTicket = ticketPriceRepository.findTicketPrice(ticketPrice.getTypeSeat(),ticketPrice.getTypeMovie(),ticketPrice.getStartTime(),ticketPrice.getEndTime(), ticketPrice.getDayOfWeeks(), ticketPrice.isHoliday());
        if(dataTicket != null){
            return dataTicket.getPrice();
        }else {
            return 0 ;
        }
    }

    public double getPriceOfSeat(ShowTime showTime , BookingRequest bookingRequest){
        List<Seat> seats = bookingRequest.getListSeatId().stream().map(id -> {
            return seatRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found seat"));
        }).toList();
        return seats.stream().map(seat -> convertSeatToPrice(seat,showTime)).reduce(0.0, Double::sum);

    }

    @Override
    public Booking findById(long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new NoSuchElementException("Not found booking"));
    }

    @Override
    public List<Booking> findAllByUserId(long userId) {
        return bookingRepository.findAllByUserId(userId);
    }
}
