package com.ra.module5_project.service;

import com.ra.module5_project.model.entity.ComboFood;
import com.ra.module5_project.model.entity.Theater;
import com.ra.module5_project.service.category.CategoryService;
import com.ra.module5_project.service.city.CityService;
import com.ra.module5_project.service.comboFood.ComboFoodService;
import com.ra.module5_project.service.movie.MovieService;
import com.ra.module5_project.service.role.RoleService;
import com.ra.module5_project.service.screenRoom.ScreenRoomService;
import com.ra.module5_project.service.theater.TheaterService;
import com.ra.module5_project.service.ticket.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitialService {
    @Autowired
    private RoleService roleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MovieService movieService ;
    @Autowired
    private CityService cityService ;
    @Autowired
    private ComboFoodService comboFoodService ;
    @Autowired
    private TheaterService theaterService ;
    @Autowired
    private TicketService ticketService ;
    @Autowired
    private ScreenRoomService screenRoomService ;

    public void initialApplication(){
        roleService.initialRole();
        categoryService.initializeCategory();
        movieService.initializeMovie();
        cityService.initializeCity();
        comboFoodService.initializeComboFood();
        theaterService.initializeTheater();
        ticketService.initializeTicketPrice();
        screenRoomService.initializeScreenRoom();
    }

}
