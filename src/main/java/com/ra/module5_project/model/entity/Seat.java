package com.ra.module5_project.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @ManyToOne()
    @JoinColumn(name = "screenId" , referencedColumnName = "id")
    private ScreenRoom screenRoom ;

    @Column(name = "seatNumber" , nullable = false)
    private String seatNumber ;

    private boolean status = true ;
}
