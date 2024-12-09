package com.ra.module5_project.service.comment;

import com.ra.module5_project.exception.CustomException;
import com.ra.module5_project.model.dto.comment.CommentDTO;
import com.ra.module5_project.model.dto.comment.CommentResponse;
import com.ra.module5_project.model.dto.comment.ReplyComment;
import com.ra.module5_project.model.dto.comment.ReplyCommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    Page<CommentResponse> findAllComments(Pageable pageable);
    List<CommentResponse> findAllByMovieId(Long movieId);
    CommentResponse createByMovieId(CommentDTO commentDTO, Long movieId) throws CustomException;
    CommentResponse updateCommentByMovieId(CommentDTO commentDTO,Long id) throws CustomException;
    void deleteCommentByMovieId(Long movieId) throws CustomException;
    CommentResponse updateStatusByCommentId(Long commentId) throws CustomException;
    ReplyCommentResponse replyToComment(Long commentId, ReplyComment replyComment) throws CustomException;
}
