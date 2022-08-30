package com.example.intermediate.controller;


import com.example.intermediate.controller.request.SubCommentRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.SubCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Validated
@RequiredArgsConstructor
@RestController
public class SubCommentController {
    private final SubCommentService subCommentService;

    @RequestMapping(value = "/api/auth/subcomment", method = RequestMethod.POST)
    public ResponseDto<?> createsubComment(@RequestBody SubCommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return subCommentService.subcreateComment(requestDto, request);
    }

    @RequestMapping(value = "/api/subcomment/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getAllComments(@PathVariable Long id) {
        return subCommentService.getAllsubCommentByComment(id);
    }

    @RequestMapping(value = "/api/auth/subcomment/{id}", method = RequestMethod.PUT)
    public ResponseDto<?> updateComment(@PathVariable Long id, @RequestBody SubCommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return subCommentService.updatesubComment(id, requestDto, request);
    }

    @RequestMapping(value = "/api/auth/subcomment/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteComment(@PathVariable Long id,
                                        HttpServletRequest request) {
        return subCommentService.deletesubComment(id, request);
    }

    @RequestMapping(value = "/api/auth/subcomment/like/{id}", method = RequestMethod.POST)
    public ResponseDto<?> createsubcommentlikes(@PathVariable Long id,
                                                HttpServletRequest request){
        return subCommentService.createsubcommentlikes(id, request);
    }
}
