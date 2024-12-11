package com.ra.module5_project.repository;

import com.ra.module5_project.model.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("select n from News n where n.title like %:keyword%")
    Page<News> findByTitle(@Param("keyword") String keyword, Pageable pageable);
    boolean existsByTitle(String title);

    @Query("select count(n) > 0 from News n where n.title like :title and n.id != :newsId")
    boolean existsByTitleAndNewsId(String title, Long newsId);
}
