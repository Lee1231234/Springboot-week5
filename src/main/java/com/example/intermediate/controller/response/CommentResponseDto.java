package com.example.intermediate.controller.response;

import java.time.LocalDateTime;
import java.util.List;

import com.example.intermediate.controller.request.SubCommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
  private Long id;
  private String author;
  private String content;
  private List<SubCommentResponeDto> Comments ;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
}
