package com.ra.module5_project.repository;

import com.ra.module5_project.model.entity.ScreenRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScreenRoomRepository extends JpaRepository<ScreenRoom,Long> {
    @Query("select s from ScreenRoom s where s.screenName like %:search%")
    Page<ScreenRoom> findAllAndSearch(Pageable pageable , @Param("search") String search);

    @Query("select count(s) > 0 from ScreenRoom s where s.screenName = :screenName and s.theater.id = :theaterId")
    boolean existsByScreenName(@Param("screenName") String screenName, @Param("theaterId") long theaterId);


}
