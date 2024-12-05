package com.ra.module5_project.model.entity;

import com.ra.module5_project.model.constant.StatusTicket;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @ManyToOne
    @JoinColumn(name = "showTimeId",referencedColumnName = "id") // lịch chiếu
    private ShowTime showTime ;

    @ManyToOne
    @JoinColumn(name = "userId" , referencedColumnName = "id")
    private User user ;

    @ManyToMany // Sử dụng ManyToMany cho mối quan hệ giữa vé và ghế
    @JoinTable(
            name = "ticket_seat", // Tên bảng liên kết
            joinColumns = @JoinColumn(name = "ticket_id"), // Tên cột trong bảng ticket
            inverseJoinColumns = @JoinColumn(name = "seat_id") // Tên cột trong bảng seat
    )
    private List<Seat> seats; // Danh sách ghế đã đặt

    @Column(name = "totalPrice" , nullable = false)
    private  double totalPrice ;

    private StatusTicket status ;
}
