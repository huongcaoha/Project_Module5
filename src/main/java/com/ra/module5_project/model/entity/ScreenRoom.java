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

    @ManyToOne
    @JoinColumn(name = "theaterId" , referencedColumnName = "id")//id rạp chiếu
    private Theater theater ;

    @Column(name = "screenName" , nullable = false) // số phòng chiếu
    private  String screenName ;

    @Column(name = "numberOfSeats" , nullable = false) // số ghế
    private int numberOfSeats ;

}
