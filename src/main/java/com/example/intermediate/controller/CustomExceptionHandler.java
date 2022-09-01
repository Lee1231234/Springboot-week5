package com.example.intermediate.controller;

import com.example.intermediate.controller.response.ResponseDto;
import org.apache.tomcat.util.http.fileupload.impl.SizeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseDto<?> handleValidationExceptions(MethodArgumentNotValidException exception) {
    String errorMessage = exception.getBindingResult()
        .getAllErrors()
        .get(0)
        .getDefaultMessage();

    return ResponseDto.fail("BAD_REQUEST", errorMessage);
  }

  @ExceptionHandler(IOException.class)
  public ResponseDto<?> IOException() {
    return ResponseDto.fail("EMPTY", "multipart file is empty");
  }

  @ExceptionHandler(SizeException.class)
  public ResponseDto<?> SizeLimitExceededException() {
    return ResponseDto.fail("Size_FAIL", "파일 크기 최대 10MB");
  }

  @ExceptionHandler(java.lang.IllegalArgumentException.class)
  public ResponseDto<?> IllegalException() {
    return ResponseDto.fail("CONVERT_FAIL", "fail convert multipart to file" );
  }
}
