package com.ra.module5_project.model.dto.comment;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentResponse {
    private String userName;
    private String comment;
    private int rating;
    private boolean status;
}
