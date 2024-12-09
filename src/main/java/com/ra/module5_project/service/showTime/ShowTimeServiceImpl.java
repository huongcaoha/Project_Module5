package com.ra.module5_project.service.showTime;

import com.ra.module5_project.model.constant.TypeMovie;
import com.ra.module5_project.model.dto.showTime.request.ShowTimeRequest;
import com.ra.module5_project.model.dto.showTime.request.ShowTimeRequestUpdate;
import com.ra.module5_project.model.dto.showTime.response.ShowTimePagination;
import com.ra.module5_project.model.entity.ShowTime;
import com.ra.module5_project.repository.ShowTimeRepository;
import com.ra.module5_project.service.movie.MovieService;
import com.ra.module5_project.service.theater.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class ShowTimeServiceImpl implements ShowTimeService{
    @Autowired
    private ShowTimeRepository showTimeRepository ;
    @Autowired
    private MovieService movieService ;
    @Autowired
    private TheaterService theaterService ;
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
        ShowTime showTime = convertDTOToShowTime(showTimeRequest);
        return showTimeRepository.save(showTime);
    }

    @Override
    public ShowTime update(long idShowTime, ShowTimeRequestUpdate showTimeRequestUpdate) {
        ShowTime oldShowTime = findById(idShowTime);
        LocalDateTime showDate = null;
        try {
            showDate = LocalDateTime.parse(showTimeRequestUpdate.getShowTime());
        }catch (Exception e){
            throw new RuntimeException("Show time invalid");
        }
        boolean checkShowTimeExist = showTimeRepository.checkShowTimeExist(showDate);
        LocalDateTime oldTime = oldShowTime.getShowTime().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime newTime = showDate.withMinute(0).withSecond(0).withNano(0);
        if(checkShowTimeExist && oldTime == newTime){
            checkShowTimeExist = false ;
        }
        if(checkShowTimeExist){
            return null ;
        }else {
            TypeMovie typeMovie = null ;
            try {
                typeMovie = TypeMovie.valueOf(showTimeRequestUpdate.getTypeMovie().toUpperCase());
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException("Invalid type seat: " + showTimeRequestUpdate.getTypeMovie());
            }
            ShowTime rs = ShowTime.builder()
                    .id(oldShowTime.getId())
                    .movie(movieService.findById(showTimeRequestUpdate.getMovieId()))
                    .showDate(showDate.toLocalDate())
                    .showTime(showDate)
                    .theater(theaterService.findById(showTimeRequestUpdate.getTheaterId()))
                    .typeMovie(typeMovie)
                    .created_date(LocalDateTime.now())
                    .build();
            return showTimeRepository.save(rs);
        }
    }

    @Override
    public ShowTime findById(long id) {
        return showTimeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found show time"));
    }

    @Override
    public void deleteById(long id) {
        ShowTime showTime = findById(id);
        showTimeRepository.deleteById(id);
    }

    @Override
    public ShowTime convertDTOToShowTime(ShowTimeRequest showTimeRequest) {
        LocalDateTime showTime = null;
        try {
            showTime = LocalDateTime.parse(showTimeRequest.getShowTime());
        }catch (Exception e){
            throw new RuntimeException("Show time invalid");
        }

        TypeMovie typeMovie = null ;
        try {
            typeMovie = TypeMovie.valueOf(showTimeRequest.getTypeMovie().toUpperCase());
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid type seat: " + showTimeRequest.getTypeMovie());
        }
        return ShowTime.builder()
                .movie(movieService.findById(showTimeRequest.getMovieId()))
                .showDate(showTime.toLocalDate())
                .showTime(showTime)
                .typeMovie(typeMovie)
                .theater(theaterService.findById(showTimeRequest.getTheaterId()))
                .created_date(LocalDateTime.now())
                .build();
    }
}
