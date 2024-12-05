package com.ra.module5_project.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "screenRoom")
public class ScreenRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @Column(name = "theaterId",nullable = false) //id rạp chiếu
    private long theaterId ;

    @Column(name = "screenNumber" , nullable = false) // số phòng chiếu
    private  int screenNumber ;

    @Column(name = "numberOfSeats" , nullable = false) // số ghế
    private int numberOfSeats ;

    private Date create_date = new Date();
}
