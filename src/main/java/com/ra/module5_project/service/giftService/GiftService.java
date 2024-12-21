package com.ra.module5_project.service.giftService;

import com.ra.module5_project.model.dto.Gift.response.GiftPagination;
import com.ra.module5_project.model.entity.Gift;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GiftService {
    GiftPagination findAll(Pageable pageable);
    Gift save(Gift gift);
    Gift update(Gift gift);
    Gift findById(long id);
    void deleteById(long id);
    List<Gift> getListCurrent();
}
