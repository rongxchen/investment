package rongxchen.investment.models;

import lombok.Data;

@Data
public class Result {

    private Integer code;

    private String message;

    private Object data;

    public static Result builder() {
        return new Result();
    }

    public Result statusCode(Integer code) {
        this.code = code;
        return this;
    }

    public Result message(String message) {
        this.message = message;
        return this;
    }

    public Result responseData(Object data) {
        this.data = data;
        return this;
    }

}
