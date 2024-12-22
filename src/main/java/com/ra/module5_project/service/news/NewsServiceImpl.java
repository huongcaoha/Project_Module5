package com.ra.module5_project.service.news;

import com.ra.module5_project.exception.BadRequestException;
import com.ra.module5_project.exception.CustomException;
import com.ra.module5_project.exception.NoResourceFoundException;
import com.ra.module5_project.model.dto.news.NewsDTO;
import com.ra.module5_project.model.dto.news.NewsUpdateDTO;
import com.ra.module5_project.model.entity.News;
import com.ra.module5_project.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Override
    public Page<News> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    @Override
    public News create(NewsDTO newsDTO) throws CustomException {
        News news = new News();
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setImage(newsDTO.getImage());
        if(newsDTO.getStart_time().isBefore(LocalDate.now())) {
            throw new CustomException("Ngày bắt đầu lớn hơn hoặc bằng ngày hiện tại");
        }
        news.setStart_time(newsDTO.getStart_time());
        if(newsDTO.getEnd_time().isBefore(LocalDate.now())) {
            throw new CustomException("Ngày kết thúc lớn hơn ngày bắt đầu");
        }
        news.setEnd_time(newsDTO.getEnd_time());
        newsRepository.save(news);
        return news;
    }

    @Override
    public News findById(Long id) {
        News news = newsRepository.findById(id).orElse(null);
        if(news == null) {
            throw new NoResourceFoundException("Tin tức không tồn tại");
        }
        return news;
    }

    @Override
    public News update(NewsUpdateDTO newsUpdateDTO, Long id) throws CustomException, BadRequestException {
        News news = findById(id);
        if(news == null) {
            throw new NoResourceFoundException("Tin tức không tồn tại");
        }

        // Nếu tên mới khác với tên hiện tại, kiểm tra sự tồn tại
        boolean  check = newsRepository.existsByTitleAndNewsId(newsUpdateDTO.getTitle(), id);
        if(check) {
            throw new BadRequestException("Tin tức đã tồn tại");
        }
        news.setTitle(newsUpdateDTO.getTitle());
        news.setContent(newsUpdateDTO.getContent());
        news.setImage(newsUpdateDTO.getImage());
        if(newsUpdateDTO.getStart_time().isBefore(LocalDate.now())) {
            throw new CustomException("Ngày bắt đầu lớn hơn hoặc bằng ngày hiện tại");
        }
        news.setStart_time(newsUpdateDTO.getStart_time());
        if(newsUpdateDTO.getEnd_time().isBefore(LocalDate.now())) {
            throw new CustomException("Ngày kết thúc lớn hơn ngày bắt đầu");
        }
        news.setEnd_time(newsUpdateDTO.getEnd_time());
        newsRepository.save(news);
        return news;
    }

    @Override
    public void delete(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public Page<News> search(String keyword, Pageable pageable) {
        return newsRepository.findByTitle(keyword, pageable);
    }
}
