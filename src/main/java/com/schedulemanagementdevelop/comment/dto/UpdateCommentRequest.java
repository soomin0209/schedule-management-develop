package com.schedulemanagementdevelop.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {

    @NotBlank(message = "댓글을 입력해주세요.")
    @Size(max = 100, message = "댓글은 100자 이내로 입력해주세요.")
    private String content;
}
