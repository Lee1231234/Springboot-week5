package com.example.intermediate.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
  private Long id;
  private String title;
  private String content;
  private String imgUrl;
  private String author;
  private int like;
  private List<CommentResponseDto> commentResponseDtoList;
  private int commentcount;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

}
