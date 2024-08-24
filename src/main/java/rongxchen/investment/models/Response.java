package rongxchen.investment.models;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response<T> {

    private Integer code;

    private String message;

    private T data;

    public static <T> Response<T> builder() {
        return new Response<T>().statusCode(HttpStatus.OK.value());
    }

    public static Response<Object> failed(String message) {
        return builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(message);
    }

    public Response<T> statusCode(Integer code) {
        this.code = code;
        return this;
    }

    public Response<T> message(String message) {
        this.message = message;
        return this;
    }

    public Response<T> body(T data) {
        this.data = data;
        return this;
    }

}
