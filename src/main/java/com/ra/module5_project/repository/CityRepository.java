package com.ra.module5_project.repository;

import com.ra.module5_project.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {
}
