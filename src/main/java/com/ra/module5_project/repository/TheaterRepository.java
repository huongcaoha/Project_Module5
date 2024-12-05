package com.ra.module5_project.repository;

import com.ra.module5_project.model.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater,Long> {
}
