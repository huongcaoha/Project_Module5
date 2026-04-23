package com.ra.module5_project.service.comboFood;

import com.ra.module5_project.model.dto.comboFoot.request.ComboFoodRequest;
import com.ra.module5_project.model.dto.comboFoot.request.ComboFoodUpdate;
import com.ra.module5_project.model.dto.comboFoot.response.ComboFoodPagination;
import com.ra.module5_project.model.entity.ComboFood;
import com.ra.module5_project.repository.ComboFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ssl.NoSuchSslBundleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ComboFoodServiceImpl implements ComboFoodService{
    @Autowired
    private ComboFoodRepository comboFoodRepository ;

    @Override
    public void initializeComboFood() {
        if (comboFoodRepository.count() == 0) {
            List<ComboFood> combos = List.of(
                    ComboFood.builder()
                            .name("Combo Đơn")
                            .description("1 Bắp lớn + 1 Nước ngọt lớn (Tùy chọn vị)")
                            .image("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2FThumbs%2F0019683.jpg&w=256&q=75")
                            .price(85000)
                            .created_at(LocalDate.now())
                            .build(),

                    ComboFood.builder()
                            .name("Combo Đôi")
                            .description("1 Bắp lớn + 2 Nước ngọt lớn (Tiết kiệm hơn)")
                            .image("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2FThumbs%2F0019006.png&w=256&q=75")
                            .price(115000)
                            .created_at(LocalDate.now())
                            .build(),

                    ComboFood.builder()
                            .name("Combo Gia Đình")
                            .description("2 Bắp lớn + 4 Nước ngọt vừa + 1 Snack khoai tây")
                            .image("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2FThumbs%2F0018379.png&w=256&q=75")
                            .price(210000)
                            .created_at(LocalDate.now())
                            .build(),

                    ComboFood.builder()
                            .name("Combo Party")
                            .description("3 Bắp lớn + 3 Nước ngọt lớn + 2 Xúc xích nướng")
                            .image("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2FThumbs%2F0013153.png&w=256&q=75")
                            .price(285000)
                            .created_at(LocalDate.now())
                            .build(),

                    ComboFood.builder()
                            .name("Combo Phim Bom Tấn")
                            .description("1 Bắp phô mai + 1 Ly nước thiết kế riêng theo phim")
                            .image("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2FThumbs%2F0018378.png&w=256&q=75")
                            .price(155000)
                            .created_at(LocalDate.now())
                            .build(),

                    ComboFood.builder()
                            .name("Combo Couple Sweet")
                            .description("1 Bắp ngọt lớn + 2 Nước suối + 1 Gói kẹo dẻo")
                            .image("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2FThumbs%2F0019006.png&w=256&q=75")
                            .price(105000)
                            .created_at(LocalDate.now())
                            .build(),

                    ComboFood.builder()
                            .name("Combo Kid")
                            .description("1 Bắp nhỏ + 1 Sữa tươi/Nước trái cây + 1 Đồ chơi nhỏ")
                            .image("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2FThumbs%2F0013153.png&w=256&q=75")
                            .price(75000)
                            .created_at(LocalDate.now())
                            .build(),

                    ComboFood.builder()
                            .name("Combo Snack Time")
                            .description("1 Khoai tây chiên lớn + 1 Nước ngọt vừa")
                            .image("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2FThumbs%2F0019560.jpg&w=256&q=75")
                            .price(65000)
                            .created_at(LocalDate.now())
                            .build(),

                    ComboFood.builder()
                            .name("Combo VIP")
                            .description("Bắp sấy mật ong + Nước ép trái cây tươi + Khăn lạnh")
                            .image("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2FThumbs%2F0018385.jpg&w=256&q=75")
                            .price(135000)
                            .created_at(LocalDate.now())
                            .build(),

                    ComboFood.builder()
                            .name("Combo Mix Vị")
                            .description("1 Bắp lớn (Mix 2 vị: Caramel/Phô mai) + 2 Nước ngọt lớn")
                            .image("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2FThumbs%2F0019012.png&w=256&q=75")
                            .price(125000)
                            .created_at(LocalDate.now())
                            .build()
            );

            comboFoodRepository.saveAll(combos);
            System.out.println(">>> Đã khởi tạo 10 Combo đồ ăn thành công!");
        }
    }

    @Override
    public ComboFoodPagination findAll(Pageable pageable) {
        Page<ComboFood> page = comboFoodRepository.findAll(pageable);
        return ComboFoodPagination.builder()
                .comboFoods(page.getContent())
                .currentPage(page.getNumber())
                .size(page.getSize())
                .totalElement(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();
    }

    @Override
    public ComboFood save(ComboFoodRequest comboFoodRequest) {
        return comboFoodRepository.save(convertRequest(comboFoodRequest));
    }

    @Override
    public ComboFood update(long id ,ComboFoodUpdate comboFoodUpdate) {
        ComboFood oldComboFood = findById(id);
        boolean checkNameExist = comboFoodRepository.checkNameExist(comboFoodUpdate.getName());
        if(checkNameExist && comboFoodUpdate.getName().equalsIgnoreCase(oldComboFood.getName())){
            checkNameExist = false ;
        }
        if(checkNameExist){
            return null;
        }else {
            ComboFood newComboFood = convertUpdate(comboFoodUpdate);
            newComboFood.setId(oldComboFood.getId());
            return comboFoodRepository.save(newComboFood);
        }

    }

    @Override
    public ComboFood findById(long id) {
        return comboFoodRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found combo food"));
    }

    @Override
    public void deleteById(long id) {
        ComboFood comboFood = findById(id);
        comboFoodRepository.deleteById(id);
    }

    @Override
    public ComboFood convertRequest(ComboFoodRequest comboFoodRequest) {
        return ComboFood.builder()
                .name(comboFoodRequest.getName())
                .description(comboFoodRequest.getDescription())
                .image(comboFoodRequest.getImage())
                .price(comboFoodRequest.getPrice())
                .build();
    }

    @Override
    public ComboFood convertUpdate(ComboFoodUpdate comboFoodUpdate) {
        return ComboFood.builder()
                .name(comboFoodUpdate.getName())
                .description(comboFoodUpdate.getDescription())
                .image(comboFoodUpdate.getImage())
                .price(comboFoodUpdate.getPrice())
                .build();
    }

    @Override
    public ComboFood updateImage(ComboFood comboFood) {
        return comboFoodRepository.save(comboFood);
    }

    @Override
    public List<ComboFood> userFindAll() {
        return comboFoodRepository.findAll();
    }


}
