package com.ra.module5_project.model.dto.ticket.response;

import com.ra.module5_project.model.entity.City;
import com.ra.module5_project.model.entity.Ticket;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TicketPagination {
    private List<Ticket> tickets ;
    private int totalPage;
    private int size ;
    private int currentPage ;
    private long totalElement ;
}
