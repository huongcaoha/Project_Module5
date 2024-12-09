package com.ra.module5_project.service.showTime;

import com.ra.module5_project.model.dto.showTime.request.ShowTimeRequest;
import com.ra.module5_project.model.dto.showTime.response.ShowTimePagination;
import com.ra.module5_project.model.entity.ShowTime;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ShowTimeService {
    ShowTimePagination findAllAndSearch(Pageable pageable , LocalDate showDate);
    ShowTime save(ShowTimeRequest showTimeRequest);
    ShowTime update(long idShowTime ,ShowTimeRequest showTimeRequest);
    ShowTime findById(long id);
    void deleteById(long id);
    ShowTime convertDTOToShowTime(ShowTimeRequest showTimeRequest);
}
