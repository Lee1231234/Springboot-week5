package com.example.intermediate.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubCommentResponeDto {
    private Long id;
    private String author;
    private String content;
    private int like;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


}
