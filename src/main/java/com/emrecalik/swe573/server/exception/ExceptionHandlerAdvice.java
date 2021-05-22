package com.emrecalik.swe573.server.exception;

import com.emrecalik.swe573.server.model.response.ApiResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<ApiResponseDto> handleAuthenticationException(AuthenticationException ex) {
        log.error("Authentication Exception: " + ex.getMessage());
        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .header("Bad Credential")
                .message("Username or password is wrong.")
                .build();
        return ResponseEntity.badRequest().body(apiResponseDto);
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<ApiResponseDto> handleExpiredJwtException(ExpiredJwtException ex) {
        log.error("Expired JWT Exception: " + ex.getMessage());
        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .header("Expired JWT")
                .message(ex.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiResponseDto);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<ApiResponseDto> handleBadRequestException(BadRequestException ex) {
        log.error("Bad Request Exception: " + ex.getMessage());
        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .header("Bad Request")
                .message(ex.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiResponseDto);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ApiResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Resource Not Found Exception: " + ex.getMessage());
        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .header("Resource Not Found")
                .message(ex.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiResponseDto);
    }

    @ExceptionHandler(value = {ExternalApiException.class})
    public ResponseEntity<ApiResponseDto> handleExternalApiException(ExternalApiException ex) {
        log.error("External Api Exception: " + ex.getMessage());
        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .header("Api Error")
                .message(ex.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiResponseDto);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Method Argument Not Valid Exception: " + ex.getMessage());
        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .header("Bad Request")
                .message(ex.getAllErrors().get(0).getDefaultMessage())
                .build();
        return ResponseEntity.badRequest().body(apiResponseDto);
    }

//    @ExceptionHandler(value = {Exception.class})
//    public ResponseEntity<ApiResponseDto> handleException(Exception ex) {
//        log.error("Exception: " + ex.getMessage());
//        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
//                .header("Error")
//                .message("Internal Server Error")
//                .build();
//        return new ResponseEntity<>(apiResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
