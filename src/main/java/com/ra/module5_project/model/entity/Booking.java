package com.ra.module5_project.model.entity;

import com.ra.module5_project.model.constant.StatusTicket;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @ManyToOne
    @JoinColumn(name = "showTimeId",referencedColumnName = "id") // lịch chiếu
    private ShowTime showTime ;

    @ManyToOne
    @JoinColumn(name = "userId" , referencedColumnName = "id")
    private User user ;

    @Column(name = "totalSeat" , nullable = false)
    private int totalSeat ;

    @Column(name = "totalPriceMovie" , nullable = false)
    private  double totalPriceMovie ;

    @Column(name = "totalPriceFood" )
    private Double totalPriceFood ; // tổng tiền đò ăn

    private boolean status =true ;
    private LocalDateTime created_at = LocalDateTime.now();

}
