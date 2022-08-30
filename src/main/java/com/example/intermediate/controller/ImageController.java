package com.example.intermediate.controller;

import com.example.intermediate.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final S3Uploader s3Uploader;

    @RequestMapping(value = "/api/upload/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.POST)
    public String upload(@RequestParam(value="image", required = false) MultipartFile multipartFile) throws IOException {

        return s3Uploader.upload(multipartFile, "static");
    }
}
