package rongxchen.investment.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class HttpException extends RuntimeException {

    protected HttpStatus status;

    protected String message;

    public HttpException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
