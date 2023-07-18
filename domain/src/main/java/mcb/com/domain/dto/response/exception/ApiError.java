package mcb.com.domain.dto.response.exception;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class ApiError {

    private int status;
    private String message;
    private List<String> errors;

    public ApiError(int status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(int status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
}
