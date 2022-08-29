package com.example.intermediate.service;


import com.example.intermediate.controller.request.SubCommentRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.controller.response.SubCommentResponeDto;
import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.SubComment;
import com.example.intermediate.domain.likes.SubCommentLikes;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubCommentService {

    private final SubCommentRepository subCommentRepository;
    private final LikesRepository likesRepository;
    private final TokenProvider tokenProvider;
    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final SubCommentLikesRepository subCommentLikesRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public ResponseDto<?> subcreateComment(SubCommentRequestDto requestDto, HttpServletRequest request) {
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


        Comment comment = commentService.isPresentComment(requestDto.getCommentId());
        if(comment== null){
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 코멘트 입니다.");
        }
        SubComment subComment = SubComment.builder()
                .member(member)
                .comment(comment)
                .content(requestDto.getContent())
                .build();
        subCommentRepository.save(subComment);
        return ResponseDto.success(
                SubCommentResponeDto.builder()
                        .id(subComment.getId())
                        .author(subComment.getMember().getNickname())
                        .content(subComment.getContent())
                        .createdAt(subComment.getCreatedAt())
                        .modifiedAt(subComment.getModifiedAt())
                        .build()
        );
    }
    @Transactional(readOnly = true)
    public ResponseDto<?> getAllsubCommentByComment(Long id) {
        Comment comment = commentService.isPresentComment(id);
        if (null == comment){
            return ResponseDto.fail("NOT_FOUND", "존재하지않는 코맨트입니다.");
        }

        List<SubComment> subComments = subCommentRepository.findAllByComment(comment);
        List<SubCommentResponeDto> subCommentResponeDtos = new ArrayList<>();


        for(SubComment subcomment:subComments){
            subCommentResponeDtos.add(
                    SubCommentResponeDto.builder()
                            .id(subcomment.getId())
                            .author(subcomment.getMember().getNickname())
                            .content(subcomment.getContent())
                            .like(subcomment.getLikes())
                            .createdAt(subcomment.getCreatedAt())
                            .modifiedAt(subcomment.getModifiedAt())
                            .build()
            );
        }


        return ResponseDto.success(subCommentResponeDtos);
    }
    @Transactional
    public ResponseDto<?> updatesubComment(Long id, SubCommentRequestDto requestDto, HttpServletRequest request) {
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

        Comment comment = commentService.isPresentComment(requestDto.getCommentId());
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 코맨트 id 입니다.");
        }

        Optional<SubComment> subComment = subCommentRepository.findById(id);
        if (subComment.isEmpty()) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        if (subComment.get().validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        SubComment subComments =subComment.get();
        SubCommentResponeDto subCommentResponeDtos = new SubCommentResponeDto();
        subComments.update(requestDto);

        return ResponseDto.success(
                    SubCommentResponeDto.builder()
                            .id(subComments.getId())
                            .author(subComments.getMember().getNickname())
                            .content(subComments.getContent())
                            .like(subComments.getLikes())
                            .createdAt(subComments.getCreatedAt())
                            .modifiedAt(subComments.getModifiedAt())
                            .build()

        );

    }
    @Transactional
    public ResponseDto<?> deletesubComment(Long id, HttpServletRequest request) {
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



        Optional<SubComment> subComment = subCommentRepository.findById(id);
        if (subComment.isEmpty()) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        if (subComment.get().validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }


        subCommentRepository.delete(subComment.get());

        return ResponseDto.success("success");
    }
    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }


    public SubCommentLikes isPresentLikes(Long Id, String nickname) {
        Optional<SubCommentLikes> optionalPostLikes = subCommentLikesRepository.findByRequestIdAndNickname(Id,nickname);
        return optionalPostLikes.orElse(null);
    }

    @Transactional
    public ResponseDto<?> createsubcommentlikes(Long id, HttpServletRequest request) {
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

        SubComment subComment = isPresentsubcomment(id);
        if (null == subComment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 코멘트 id 입니다.");
        }

        SubCommentLikes likes  = isPresentLikes(subComment.getId(), member.getNickname());

        if (null == likes)
            subCommentLikesRepository.save(SubCommentLikes.builder().requestId(subComment.getId()).nickname(member.getNickname()).build());
        else
            subCommentLikesRepository.delete(likes);

        subComment.updatelikes(likesRepository.findAllByRequestId(subComment.getId()).size());

        return ResponseDto.success("like success");

    }
    @Transactional(readOnly = true)
    public SubComment isPresentsubcomment(Long id) {
        Optional<SubComment> optionalPost = subCommentRepository.findById(id);
        return optionalPost.orElse(null);
    }


}
