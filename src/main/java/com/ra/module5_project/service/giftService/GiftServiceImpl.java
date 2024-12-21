package com.ra.module5_project.service.giftService;

import com.ra.module5_project.model.dto.Gift.response.GiftPagination;
import com.ra.module5_project.model.entity.Gift;
import com.ra.module5_project.repository.GiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GiftServiceImpl implements GiftService{
    @Autowired
    private GiftRepository giftRepository ;

    @Override
    public GiftPagination findAll(Pageable pageable) {
        Page<Gift> page = giftRepository.findAll(pageable);
        return GiftPagination.builder()
                .gifts(page.getContent())
                .size(page.getSize())
                .currentPage(page.getNumber())
                .totalElement(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();
    }

    @Override
    public Gift save(Gift gift) {
        return giftRepository.save(gift);
    }

    @Override
    public Gift update(Gift gift) {
        return giftRepository.save(gift);
    }

    @Override
    public Gift findById(long id) {
        return giftRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found gift"));
    }

    @Override
    public void deleteById(long id) {
        Gift gift = findById(id);
        giftRepository.deleteById(id);
    }

    @Override
    public List<Gift> getListCurrent() {
        LocalDate localDate = LocalDate.now();
        return giftRepository.getListCurrent(localDate);
    }
}
