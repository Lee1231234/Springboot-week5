//package com.example.intermediate.service;
//
//import com.example.intermediate.controller.request.PostRequestDto;
//import com.example.intermediate.controller.response.ImageResponseDto;
//import com.example.intermediate.controller.response.ResponseDto;
//import com.example.intermediate.domain.Image;
//import com.example.intermediate.domain.Member;
//import com.example.intermediate.jwt.TokenProvider;
//import com.example.intermediate.repository.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Service
//@RequiredArgsConstructor
//public class ImageService {
//    private final PostRepository postRepository;
//    private final ImageRepository imageRepository;
//    private final TokenProvider tokenProvider;
//
//
//    @Transactional
//    public ResponseDto<?> upload(HttpServletRequest request, @RequestParam(value="image", required = false) MultipartFile multipartFile) {
//        if (null == request.getHeader("Refresh-Token")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
//        if (null == request.getHeader("Authorization")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
//        Member member = validateMember(request);
//        if (null == member) {
//            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//        }
//
//        imageRepository.save(String imgUrl);
//        return ResponseDto.success(
//                ImageResponseDto.builder()
//                .data(image.getImgUrl())
//                .build()
//        );
//    }
//    @Transactional
//    public Member validateMember(HttpServletRequest request) {
//        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
//            return null;
//        }
//        return tokenProvider.getMemberFromAuthentication();
//    }
//}
