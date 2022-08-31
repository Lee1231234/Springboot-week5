package com.example.intermediate.service;


import com.example.intermediate.controller.response.CommentResponseDto;
import com.example.intermediate.controller.response.MyPageResponeDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.controller.response.SubCommentResponeDto;
import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.Post;
import com.example.intermediate.domain.SubComment;
import com.example.intermediate.domain.likes.CommentLikes;
import com.example.intermediate.domain.likes.PostLikes;
import com.example.intermediate.domain.likes.SubCommentLikes;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final TokenProvider tokenProvider;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostRepository postRepository;
    private final LikesRepository likesRepository;
    private final SubCommentRepository subCommentRepository;
    private final SubCommentLikesRepository subCommentLikesRepository;

    @Transactional(readOnly = true)
    public  ResponseDto<?> mypage(HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        //포스트 코맨트 서브코맨트 불러오기
        List<Post> posts =postRepository.findAllByMember(member);
        List<Comment> comments = commentRepository.findAllByMember(member);
        List<SubComment> subComments = subCommentRepository.findAllByMember(member);
        //포스트라이크 코맨트라이크 서브코맨트라이크 블러오기
        List<PostLikes> postLikes = likesRepository.findAllByNickname(member.getNickname());
        List<CommentLikes> commentLikes = commentLikeRepository.findAllByNickname(member.getNickname());
        List<SubCommentLikes> subCommentLikes = subCommentLikesRepository.findAllByNickname(member.getNickname());
        //조합



        return ResponseDto.success(
                MyPageResponeDto.builder()
                        .MyPost(posts)
                        .MyComment(commentResponseDtos(comments))
                        .MySubComment(subCommentResponeDtos(subComments))
                        .LikePost(postlike(postLikes))
                        .LikeComment(commentlike(commentLikes))
                        .LikeSubComment(subcommentlike(subCommentLikes))
                        .build()
        );

    }
    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    public List<CommentResponseDto> commentResponseDtos(List<Comment>comments){
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : comments) {
            List<SubComment> subComments = subCommentRepository.findAllByComment(comment);
            List<SubCommentResponeDto> subCommentResponeDtos = new ArrayList<>();
            for (SubComment subcomment : subComments) {
                subCommentResponeDtos.add(
                        SubCommentResponeDto.builder()
                                .id(subcomment.getId())
                                .author(subcomment.getMember().getNickname())
                                .content(subcomment.getContent())
                                .createdAt(subcomment.getCreatedAt())
                                .modifiedAt(subcomment.getModifiedAt())
                                .build()
                );
            }
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .author(comment.getMember().getNickname())
                            .content(comment.getContent())
                            .Comments(subCommentResponeDtos)
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }
        return commentResponseDtoList;
    }

    public List<SubCommentResponeDto> subCommentResponeDtos(List<SubComment>subComments){


        List<SubCommentResponeDto> subCommentResponeDtos = new ArrayList<>();


        for(SubComment subcomment:subComments){
            subCommentResponeDtos.add(
                    SubCommentResponeDto.builder()
                            .id(subcomment.getId())
                            .author(subcomment.getMember().getNickname())
                            .content(subcomment.getContent())
                            .createdAt(subcomment.getCreatedAt())
                            .modifiedAt(subcomment.getModifiedAt())
                            .build()
            );
        }
        return subCommentResponeDtos;
    }

    public List<SubCommentResponeDto> subcommentlike(List<SubCommentLikes>subCommentLikes){
        List<SubComment> subcomments = new ArrayList<>();

        for(SubCommentLikes subcommentLike:subCommentLikes){
            subcomments.add(subCommentRepository.findById(subcommentLike.getRequestId()).get());
        }
        return subCommentResponeDtos(subcomments);
    }
    public List<CommentResponseDto> commentlike(List<CommentLikes>commentLikes){
        List<Comment> comments = new ArrayList<>();
        for(CommentLikes commentLike:commentLikes){
            comments.add(commentRepository.findById(commentLike.getRequestId()).get());
        }
        return commentResponseDtos(comments);
    }
    public List<Post> postlike(List<PostLikes>postLikes){
        List<Post> posts = new ArrayList<>();
        for(PostLikes postlike:postLikes){
            posts.add(postRepository.findById(postlike.getRequestId()).get());
        }
        return posts;
    }
}
