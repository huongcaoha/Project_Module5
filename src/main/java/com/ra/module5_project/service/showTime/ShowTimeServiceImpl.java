package com.ra.module5_project.service.showTime;

import com.ra.module5_project.model.dto.showTime.request.ShowTimeRequest;
import com.ra.module5_project.model.dto.showTime.response.ShowTimePagination;
import com.ra.module5_project.model.entity.ShowTime;
import com.ra.module5_project.repository.ShowTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ShowTimeServiceImpl implements ShowTimeService{
    @Autowired
    private ShowTimeRepository showTimeRepository ;
    @Override
    public ShowTimePagination findAllAndSearch(Pageable pageable, LocalDate showDate) {
        Page<ShowTime> page = null ;
        if(showDate != null){
            page = showTimeRepository.findAllAndSearchDate(pageable,showDate);
        }else {
            page = showTimeRepository.findAll(pageable);
        }
        return ShowTimePagination.builder()
                .showTimes(page.getContent())
                .currentPage(page.getNumber())
                .size(page.getSize())
                .totalElement(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();
    }

    @Override
    public ShowTime save(ShowTimeRequest showTimeRequest) {
        return null;
    }

    @Override
    public ShowTime update(long idShowTime, ShowTimeRequest showTimeRequest) {
        return null;
    }

    @Override
    public ShowTime findById(long id) {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public ShowTime convertDTOToShowTime(ShowTimeRequest showTimeRequest) {
        return ShowTime.builder()

                .build();
    }
}
