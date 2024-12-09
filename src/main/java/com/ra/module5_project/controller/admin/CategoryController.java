package com.ra.module5_project.controller.admin;

import com.ra.module5_project.model.dto.category.CategoryDTO;
import com.ra.module5_project.model.dto.category.CategoryUpdateDTO;
import com.ra.module5_project.model.entity.Category;
import com.ra.module5_project.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Category> categories = categoryService.findAll(pageable);
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryService.create(categoryDTO);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CategoryUpdateDTO categoryUpdateDTO) {
        Category updateCategory = categoryService.update(categoryUpdateDTO, id);
        return ResponseEntity.ok(updateCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> findByName(@RequestParam String keyword,@PageableDefault(page = 0, size = 3,sort = "id",direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Category> categories;
        if(keyword == null || keyword.isEmpty()) {
            categories = categoryService.findAll(pageable);
        } else {
            categories = categoryService.findByCategoryName(keyword, pageable);
        }
        return ResponseEntity.ok(categories);
    }
}
