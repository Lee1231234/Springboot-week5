package com.example.intermediate.controller.response;


import com.example.intermediate.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageResponeDto {
    private List<Post> MyPost;
    private List<CommentResponseDto> MyComment;
    private List<SubCommentResponeDto> MySubComment;
    private List<Post> LikePost;
    private List<CommentResponseDto> LikeComment;
    private List<SubCommentResponeDto> LikeSubComment;
}
