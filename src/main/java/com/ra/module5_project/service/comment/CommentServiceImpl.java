package com.ra.module5_project.service.comment;

import com.ra.module5_project.exception.CustomException;
import com.ra.module5_project.exception.NoResourceFoundException;
import com.ra.module5_project.model.dto.comment.CommentDTO;
import com.ra.module5_project.model.dto.comment.CommentResponse;
import com.ra.module5_project.model.dto.comment.ReplyComment;
import com.ra.module5_project.model.dto.comment.ReplyCommentResponse;
import com.ra.module5_project.model.entity.Comment;
import com.ra.module5_project.model.entity.Movie;
import com.ra.module5_project.repository.CommentRepository;
import com.ra.module5_project.repository.MovieRepository;
import com.ra.module5_project.repository.UserRepository;
import com.ra.module5_project.security.principle.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<CommentResponse> findAllComments(Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);
        List<CommentResponse> responses = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponse commentResponse = CommentResponse.builder()
                    .userName(comment.getUser().getUsername())
                    .comment(comment.getComment())
                    .rating(comment.getRating())
                    .status(comment.isStatus())
                    .build();
            responses.add(commentResponse);
        }
        return new PageImpl<>(responses, pageable, comments.getTotalElements());
    }

    //Hiển thị các comment  trong phim(có status = true)
    @Override
    public List<CommentResponse> findAllByMovieId(Long movieId) throws NoResourceFoundException {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if(movie == null) {
            throw new NoResourceFoundException("Phim không tồn tại");
        }
        List<Comment> comments = commentRepository.findAllByMovieIdAndStatus(movieId,true);
        List<CommentResponse> responses = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponse commentResponse = CommentResponse.builder()
                    .userName(comment.getUser().getUsername())
                    .comment(comment.getComment())
                    .rating(comment.getRating())
                    .status(comment.isStatus())
                    .build();
            responses.add(commentResponse);
        }
        return responses;
    }

    @Override
    public CommentResponse createByMovieId(CommentDTO commentDTO, Long movieId) throws CustomException {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if(movie == null) {
            throw new NoResourceFoundException("Phim không tồn tại");
        }
        List<Comment> comments = commentRepository.findAllByMovieId(movieId);
        boolean check =comments.stream()
                .anyMatch(comment -> comment.getUser().getUsername()
                        .equals(userPrinciple.getUsername()));
        if(check) {
            throw new CustomException("Bạn đã bình luận sản phẩm này rồi");
        }
        Comment newComment = new Comment();
        newComment.setUser(userPrinciple.getUser());
        newComment.setMovie(movie);
        newComment.setComment(commentDTO.getComment());
        newComment.setRating(commentDTO.getRating());
        newComment.setStatus(true);
        commentRepository.save(newComment);

        return CommentResponse.builder()
                .userName(userPrinciple.getUsername())
                .comment(newComment.getComment())
                .rating(newComment.getRating())
                .status(newComment.isStatus())
                .build();
    }

    @Override
    public CommentResponse updateCommentByMovieId(CommentDTO commentDTO, Long movieId) throws CustomException {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if(movie == null) {
            throw new NoResourceFoundException("Phim không tồn tại");
        }
        List<Comment> comments = commentRepository.findAllByMovieId(movieId);
        Comment updateComment = comments.stream()
                .filter(comment -> comment.getUser().getUsername().equals(userPrinciple.getUsername()))
                .findFirst().orElse(null);
        if(updateComment == null) {
            throw new CustomException("Bạn chưa bình luận sản phẩm này");
        }
        updateComment.setComment(commentDTO.getComment());
        updateComment.setRating(commentDTO.getRating());
        commentRepository.save(updateComment);
        return CommentResponse.builder()
                .userName(userPrinciple.getUsername())
                .comment(updateComment.getComment())
                .rating(updateComment.getRating())
                .status(updateComment.isStatus())
                .build();
    }

    @Override
    public void deleteCommentByMovieId(Long movieId) throws CustomException {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if(movie == null) {
            throw new NoResourceFoundException("Phim không tồn tại");
        }
        List<Comment> comments = commentRepository.findAllByMovieId(movieId);
        boolean check =comments.stream()
                .anyMatch(comment -> comment.getUser().getUsername()
                        .equals(userPrinciple.getUsername()));
        if(!check) {
            throw new CustomException("Bạn chưa bình luận sản phẩm này");
        }
        commentRepository.deleteByMovieId(movieId);
    }

    @Override
    public CommentResponse updateStatusByCommentId(Long commentId) throws CustomException {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if(comment == null) {
            throw new CustomException("Comment không tồn tại");
        }
        comment.setStatus(!comment.isStatus());
        commentRepository.save(comment);
        return CommentResponse.builder()
                .userName(comment.getUser().getUsername())
                .comment(comment.getComment())
                .rating(comment.getRating())
                .status(comment.isStatus())
                .build();
    }


    @Override
    public ReplyCommentResponse replyToComment(Long commentId, ReplyComment replyComment) throws CustomException {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Comment parentComment = commentRepository.findById(commentId).orElse(null);
        if(parentComment == null) {
            throw new CustomException("Bình luận không tồn tại");
        }

        Comment newComment = Comment.builder()
                .user(userPrinciple.getUser())
                .comment(replyComment.getReplyComment())
                .status(true)
                .build();
        commentRepository.save(newComment);
        return ReplyCommentResponse.builder()
                .userName(userPrinciple.getUser().getUsername())
                .comment(parentComment.getComment())
                .reply(newComment.getComment())
                .build();
    }
}
