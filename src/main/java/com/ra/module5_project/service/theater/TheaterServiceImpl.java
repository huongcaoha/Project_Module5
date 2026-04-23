package com.ra.module5_project.service.theater;

import com.ra.module5_project.model.dto.theater.request.TheaterRequest;
import com.ra.module5_project.model.dto.theater.request.TheaterRequestUpdate;
import com.ra.module5_project.model.dto.theater.response.TheaterPagination;
import com.ra.module5_project.model.entity.City;
import com.ra.module5_project.model.entity.Theater;
import com.ra.module5_project.repository.CityRepository;
import com.ra.module5_project.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TheaterServiceImpl implements TheaterService{
    @Autowired
    private TheaterRepository theaterRepository ;

    @Override
    public void initializeTheater() {
        if (theaterRepository.count() == 0) {
            List<Theater> theaters = List.of(
                    Theater.builder()
                            .name("Rikkei Cinema Hà Đông")
                            .address("Tầng 4, Mê Linh Plaza, Hà Đông, Hà Nội")
                            .phoneNumber("024.1234.5678")
                            .numberOfScreens(8)
                            .build(),

                    Theater.builder()
                            .name("Rikkei Cinema Giải Phóng")
                            .address("Số 123 Giải Phóng, Hai Bà Trưng, Hà Nội")
                            .phoneNumber("024.8888.9999")
                            .numberOfScreens(6)
                            .build(),

                    Theater.builder()
                            .name("Rikkei Cinema Quận 1")
                            .address("Số 1 Lê Duẩn, Bến Nghé, Quận 1, TP. HCM")
                            .phoneNumber("028.3333.4444")
                            .numberOfScreens(10)
                            .build(),

                    Theater.builder()
                            .name("Rikkei Cinema Thảo Điền")
                            .address("Vincom Mega Mall, Thảo Điền, Quận 2, TP. HCM")
                            .phoneNumber("028.5555.6666")
                            .numberOfScreens(7)
                            .build(),

                    Theater.builder()
                            .name("Rikkei Cinema Đà Nẵng Riverside")
                            .address("Số 2 đường 2/9, Bình Hiên, Hải Châu, Đà Nẵng")
                            .phoneNumber("0236.111.222")
                            .numberOfScreens(5)
                            .build(),

                    Theater.builder()
                            .name("Rikkei Cinema Cần Thơ")
                            .address("Tòa nhà TTC, Hai Bà Trưng, Ninh Kiều, Cần Thơ")
                            .phoneNumber("0292.444.555")
                            .numberOfScreens(4)
                            .build(),

                    Theater.builder()
                            .name("Rikkei Cinema Hải Phòng")
                            .address("Lê Hồng Phong, Đông Khê, Ngô Quyền, Hải Phòng")
                            .phoneNumber("0225.777.888")
                            .numberOfScreens(6)
                            .build(),

                    Theater.builder()
                            .name("Rikkei Cinema Nha Trang")
                            .address("Số 60 Thái Nguyên, Phương Sài, Nha Trang")
                            .phoneNumber("0258.999.000")
                            .numberOfScreens(5)
                            .build(),

                    Theater.builder()
                            .name("Rikkei Cinema Biên Hòa")
                            .address("Phạm Văn Thuận, Tân Mai, Biên Hòa, Đồng Nai")
                            .phoneNumber("0251.222.333")
                            .numberOfScreens(6)
                            .build(),

                    Theater.builder()
                            .name("Rikkei Cinema Vũng Tàu")
                            .address("Số 36 Nguyễn Thái Học, Phường 7, Vũng Tàu")
                            .phoneNumber("0254.666.777")
                            .numberOfScreens(4)
                            .build()
            );

            theaterRepository.saveAll(theaters);
            System.out.println(">>> Đã khởi tạo 10 cụm rạp chiếu phim thành công!");
        }
    }

    @Override
    public TheaterPagination findAllAndSearch(Pageable pageable, String search) {
        Page<Theater> page = null ;
        if(search != null){
            page = theaterRepository.findAllAndSearchName(pageable, search);
        }else {
            page = theaterRepository.findAll(pageable);
        }
        return TheaterPagination.builder()
                .theaters(page.getContent())
                .currentPage(page.getNumber())
                .totalElement(page.getTotalElements())
                .size(page.getSize())
                .totalPage(page.getTotalPages())
                .build();
    }

    @Override
    public Theater save(TheaterRequest theaterRequest) {
        Theater theater = convertToTheater(theaterRequest);
        return theaterRepository.save(theater);
    }

    @Override
    public Theater update(long id , TheaterRequestUpdate theaterRequestUpdate) {
        Theater oldTheater = findById(id);
        boolean checkTheaterNameExist = theaterRepository.checkTheaterNameExist(theaterRequestUpdate.getName());
        if(checkTheaterNameExist && oldTheater.getName().equalsIgnoreCase(theaterRequestUpdate.getName())){
            checkTheaterNameExist = false ;
        }
        if(checkTheaterNameExist){
            return null ;
        }else {
            Theater theater = Theater.builder()
                    .id(oldTheater.getId())
                    .address(theaterRequestUpdate.getAddress())
                    .name(theaterRequestUpdate.getName())
                    .numberOfScreens(theaterRequestUpdate.getNumberOfScreens())
                    .phoneNumber(theaterRequestUpdate.getPhoneNumber())
                    .build();
            return theaterRepository.save(theater);
        }
    }

    @Override
    public void deleteById(long id) {
        Theater theater = findById(id);
        theaterRepository.deleteById(id);
    }

    @Override
    public Theater findById(long id) {
        return theaterRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found theater"));
    }

    @Override
    public boolean checkNameExist(String name) {
        return theaterRepository.checkTheaterNameExist(name);
    }

    @Override
    public List<Theater> getTheaters() {
        return theaterRepository.findAll();
    }

    @Override
    public List<Theater> getTheaterByCity(String cityName) {
       return theaterRepository.getTheaterByCity(cityName);
    }

    Theater convertToTheater(TheaterRequest theaterRequest){
        return Theater.builder()
                .address(theaterRequest.getAddress())
                .name(theaterRequest.getName())
                .numberOfScreens(theaterRequest.getNumberOfScreens())
                .phoneNumber(theaterRequest.getPhoneNumber())
                .build();
    }
}
