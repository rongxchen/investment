package rongxchen.investment.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rongxchen.investment.models.Response;

import java.lang.reflect.UndeclaredThrowableException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UndeclaredThrowableException.class)
    public ResponseEntity<String> handleUnauthorizedException(HttpException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(HttpException.class)
    public Response<Object> handleHttpException(HttpException e) {
        return Response.builder()
                .statusCode(e.getStatus().value())
                .message(e.getMessage());
    }

    @ExceptionHandler(DataException.class)
    public Response<Object> handleDataException(DataException e) {
        return Response.builder()
                .statusCode(500)
                .message(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Response<Object> handleException(Exception e) {
        return Response.builder()
                .statusCode(500)
                .message(e.getMessage());
    }

}
