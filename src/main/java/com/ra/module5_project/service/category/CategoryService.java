package com.ra.module5_project.service.category;

import com.ra.module5_project.model.dto.category.CategoryDTO;
import com.ra.module5_project.model.dto.category.CategoryUpdateDTO;
import com.ra.module5_project.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<Category> findAll(Pageable pageable);
    Category create(CategoryDTO categoryDTO);
    Category findById(Long id);
    Category update(CategoryUpdateDTO categoryUpdateDTO, Long id);
    void delete(Long id);
    Page<Category> findByCategoryName(String keyword, Pageable pageable);
    Page<Category>findAllByStatus(Pageable pageable);
}
