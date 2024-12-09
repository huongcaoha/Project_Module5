package com.ra.module5_project.model.dto.comment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentDTO {
    @NotBlank(message = "Comment không được để trống")
    private String comment;

    @Min(value = 1, message = "Rating phải lớn hơn hoặc bằng 1")
    @Max(value = 5, message = "Rating phải nhỏ hơn hoặc bằng 5")
    private int rating;
}
