package com.example.intermediate.controller;


import com.example.intermediate.controller.request.MemberRequestDto;
import com.example.intermediate.controller.response.MyPageResponeDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MyPageController {
    private final MyPageService myPageService;
    @RequestMapping(value = "/api/auth/mypage", method = RequestMethod.GET)
    public ResponseDto<?> mypage(HttpServletRequest request) {
        return myPageService.mypage(request);
    }
}
