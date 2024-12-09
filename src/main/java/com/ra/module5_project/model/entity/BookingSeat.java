package com.ra.module5_project.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "BookingSeat")
public class BookingSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @ManyToOne
    @JoinColumn(name = "bookingId" ,referencedColumnName = "id")
    private Booking booking ;

    @ManyToOne
    @JoinColumn(name = "seatId" , referencedColumnName = "id")
    private Seat seat ;

    @Column(name = "quantity" , nullable = false)
    private int quantity ;

    private LocalDate created_date ;
    private LocalDate update_date ;
}
