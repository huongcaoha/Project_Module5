package com.ra.module5_project.service.screenRoom;

import com.ra.module5_project.model.dto.screenRoom.request.ScreenRoomRequest;
import com.ra.module5_project.model.dto.screenRoom.response.ScreenRoomPagination;
import com.ra.module5_project.model.dto.screenRoom.response.ScreenRoomResponse;
import com.ra.module5_project.model.entity.ScreenRoom;
import com.ra.module5_project.repository.ScreenRoomRepository;
import com.ra.module5_project.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ScreenRoomServiceImpl implements ScreenRoomService{
    @Autowired
    private ScreenRoomRepository screenRoomRepository ;
    @Autowired
    private TheaterRepository theaterRepository ;
    @Override
    public ScreenRoomPagination findAllAndSearch(Pageable pageable, String search) {
        Page<ScreenRoom> page = null ;
        if(search != null){
            page = screenRoomRepository.findAllAndSearch(pageable, search);
        }else {
            page = screenRoomRepository.findAll(pageable);
        }
        return ScreenRoomPagination.builder()
                .screenRooms(page.getContent())
                .currentPage(page.getNumber())
                .size(page.getSize())
                .totalElement(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();
    }

    @Override
    public ScreenRoomResponse save(ScreenRoomRequest screenRoomRequest) {
        ScreenRoom screenRoom = convertToScreenRoom(screenRoomRequest);
        ScreenRoom newScreenRoom = screenRoomRepository.save(screenRoom);
        return convertToScreenRoomResponse(newScreenRoom);
    }

    @Override
    public ScreenRoomResponse update(long id, ScreenRoomRequest screenRoomRequest) {
        ScreenRoomResponse screenRoomOld = findById(id);
        boolean checkScreenNumberExist = screenRoomRepository.existsByScreenNumber(screenRoomRequest.getScreenNumber());
        if(checkScreenNumberExist && screenRoomOld.getScreenNumber() == screenRoomRequest.getScreenNumber()){
            checkScreenNumberExist = false ;
        }

        if(checkScreenNumberExist){
            return  null ;
        }else {
            ScreenRoom screenRoom = convertToScreenRoom(screenRoomRequest);
            screenRoom.setId(screenRoomOld.getId());
            return convertToScreenRoomResponse(screenRoomRepository.save(screenRoom));
        }
    }

    @Override
    public ScreenRoomResponse findById(long id) {
        ScreenRoom screenRoom =  screenRoomRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found screen room"));
        return convertToScreenRoomResponse(screenRoom);
    }

    @Override
    public void deleteById(long id) {
        ScreenRoomResponse screenRoom = findById(id);
        screenRoomRepository.deleteById(id);

    }

    @Override
    public ScreenRoom convertToScreenRoom(ScreenRoomRequest screenRoomRequest) {
        return ScreenRoom.builder()
                .theater(theaterRepository.findById(screenRoomRequest.getTheaterId()).orElseThrow(() -> new NoSuchElementException("Not found theater")))
                .screenNumber(screenRoomRequest.getScreenNumber())
                .numberOfSeats(screenRoomRequest.getNumberOfSeats())
                .build();
    }

    @Override
    public ScreenRoomResponse convertToScreenRoomResponse(ScreenRoom screenRoom) {
       return ScreenRoomResponse.builder()
                .id(screenRoom.getId())
                .numberOfSeats(screenRoom.getNumberOfSeats())
                .screenNumber(screenRoom.getScreenNumber())
                .theaterName(screenRoom.getTheater().getName())
                .build();
    }
}
