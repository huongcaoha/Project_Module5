package com.ra.module5_project.service.showTime;

import com.ra.module5_project.model.dto.showTime.response.ShowTimePagination;
import org.springframework.data.domain.Pageable;

public interface ShowTimeService {
    ShowTimePagination findAllAndSearch(Pageable pageable ,String search);

}
