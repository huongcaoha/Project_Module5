package com.ra.module5_project.model.dto.comment;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReplyCommentResponse {
    private String userName;
    private String comment;
    private String reply;
}
