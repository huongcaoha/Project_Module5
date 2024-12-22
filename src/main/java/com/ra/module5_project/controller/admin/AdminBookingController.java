package com.ra.module5_project.controller.admin;

import com.ra.module5_project.model.dto.booking.request.BookingSearch;
import com.ra.module5_project.model.dto.booking.response.BookingPagination;
import com.ra.module5_project.model.entity.Booking;
import com.ra.module5_project.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myService.com/v1/admin/bookings")
public class AdminBookingController {
    @Autowired
    private BookingService bookingService ;
    @GetMapping
    public ResponseEntity<BookingPagination> findAll(@PageableDefault(page = 0 , size = 5 , sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                     @RequestParam(required = false)BookingSearch search){
        return new ResponseEntity<>(bookingService.findAll(pageable ,search), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> findById(@PathVariable long id){
        return new ResponseEntity<>(bookingService.findById(id),HttpStatus.OK);
    }
}
