package com.ra.module5_project.service.category;

import com.ra.module5_project.model.entity.Category;
import com.ra.module5_project.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository; // Giả lập Repository

    @InjectMocks
    private CategoryServiceImpl categoryService; // Tiêm giả lập vào Service thực

    @Test
    void testFindAll_ShouldReturnList() {
        // 1. Giả lập dữ liệu (Given)
        List<Category> mockData = List.of(new Category(1L, "Action Movie","phim hay",true));
        when(categoryRepository.findAll()).thenReturn(mockData);

        // 2. Chạy hàm thực tế (When)
        List<Category> result = categoryService.findAll();

        // 3. Kiểm tra kết quả (Then)
        assertEquals(1, result.size());
        assertEquals("Action Movie", result.getFirst().getCategoryName());
    }
}