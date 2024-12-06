package com.ra.module5_project.controller.admin;

import com.ra.module5_project.model.dto.screenRoom.request.ScreenRoomRequest;
import com.ra.module5_project.model.dto.screenRoom.response.ScreenRoomPagination;
import com.ra.module5_project.model.dto.screenRoom.response.ScreenRoomResponse;
import com.ra.module5_project.service.screenRoom.ScreenRoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myService.com/v1/admin/screenRooms")
public class AdminScreenRoomController {
    @Autowired
    private ScreenRoomService screenRoomService ;
    @GetMapping
    public ResponseEntity<ScreenRoomPagination> findAllAndSearch(@PageableDefault(page = 0 , size = 5 , sort = "id" , direction = Sort.Direction.ASC)Pageable pageable,
                                                                 @RequestParam(required = false) String search){
        return new ResponseEntity<>(screenRoomService.findAllAndSearch(pageable, search), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ScreenRoomResponse> save(@Valid @RequestBody ScreenRoomRequest screenRoomRequest){
        return new ResponseEntity<>(screenRoomService.save(screenRoomRequest),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id ,
                                    @Valid @RequestBody ScreenRoomRequest screenRoomRequest){
        ScreenRoomResponse screenRoomResponse = screenRoomService.update(id, screenRoomRequest);
        if(screenRoomResponse != null){
            return  new ResponseEntity<>(screenRoomResponse,HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Room number existed" , HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id){
        return new ResponseEntity<>("Delete successfully",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreenRoomResponse> findById(@PathVariable long id){
        return new ResponseEntity<>(screenRoomService.findById(id),HttpStatus.OK);
    }
}
