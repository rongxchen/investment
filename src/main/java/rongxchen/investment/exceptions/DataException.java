package rongxchen.investment.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DataException extends RuntimeException {

    public DataException(String message) {
        super(message);
    }

}
