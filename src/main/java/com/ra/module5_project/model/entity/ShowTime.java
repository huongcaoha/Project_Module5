package com.ra.module5_project.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "showTime") // lịch chiếu
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @ManyToOne
    @JoinColumn(name = "movieId",referencedColumnName = "id")
    private Movie movie ;

    @Column(name = "showDate",nullable = false)
    private Date showDate ;

    @Column(name = "showTime" ,nullable = false)
    private LocalDateTime showTime ;

    @ManyToOne
    @JoinColumn(name = "theaterId" , referencedColumnName = "id")
    private Theater theater ;
}
