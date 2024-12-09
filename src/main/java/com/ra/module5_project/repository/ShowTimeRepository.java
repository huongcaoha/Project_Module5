package com.ra.module5_project.repository;

import com.ra.module5_project.model.entity.ShowTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ShowTimeRepository extends JpaRepository<ShowTime,Long> {
    @Query("select s from ShowTime s where s.showDate = :showDate")
    Page<ShowTime> findAllAndSearchDate(Pageable pageable ,@Param("showDate") LocalDate showDate);

    @Query("SELECT COUNT(s) > 0 FROM ShowTime s WHERE "
            + "FUNCTION('YEAR', s.showTime) = FUNCTION('YEAR', :showTime) AND "
            + "FUNCTION('MONTH', s.showTime) = FUNCTION('MONTH', :showTime) AND "
            + "FUNCTION('DAY', s.showTime) = FUNCTION('DAY', :showTime) AND "
            + "FUNCTION('HOUR', s.showTime) = FUNCTION('HOUR', :showTime)")
    boolean checkShowTimeExist(@Param("showTime") LocalDateTime showTime);
}
