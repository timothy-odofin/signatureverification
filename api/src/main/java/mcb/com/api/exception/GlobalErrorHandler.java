package mcb.com.api.exception;


import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import mcb.com.domain.dto.response.ApiResponse;
import mcb.com.domain.dto.response.exception.BadRequestException;
import mcb.com.domain.dto.response.exception.UserNotFoundException;
import org.hibernate.PropertyAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static mcb.com.api.utils.MessageUtil.FAIL;
import static mcb.com.api.utils.MessageUtil.INTERNAL_ERROR;

@ControllerAdvice
@Slf4j
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest webRequest) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : exception.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        logError(exception, webRequest);
        return ResponseEntity.ok(new ApiResponse<>(FAIL,HttpStatus.BAD_REQUEST.value() , String.join(",", errors))
        );

    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUserNotFoundException(UserNotFoundException exception, WebRequest webRequest) {
        return exceptionMessage(exception, webRequest,HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiResponse<String>> handleSqlException(SQLException exception, WebRequest webRequest) {
        return exceptionMessage(exception, webRequest,HttpStatus.INTERNAL_SERVER_ERROR.value(), INTERNAL_ERROR);

    }

    @ExceptionHandler(PropertyAccessException.class)
    public ResponseEntity<ApiResponse<String>> handlePropertyAccessException(PropertyAccessException exception, WebRequest webRequest) {
        return exceptionMessage(exception, webRequest,HttpStatus.INTERNAL_SERVER_ERROR.value(), INTERNAL_ERROR);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleBadRequestExceptions(BadRequestException exception, WebRequest webRequest) {
        return exceptionMessage(exception, webRequest,HttpStatus.BAD_REQUEST.value(), exception.getMessage());

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest webRequest) {
        return exceptionMessage(exception, webRequest,HttpStatus.EXPECTATION_FAILED.value(), exception.getMessage());

    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException exception, WebRequest webRequest) {
        logError(exception, webRequest);
        String error =
                exception.getName() + " should be of type " + exception.getRequiredType().getName();
        return exceptionMessage(exception, webRequest, HttpStatus.BAD_REQUEST.value(), error);

    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<String>> handlerGlobalError(Exception exception, WebRequest webRequest) {
        return exceptionMessage(exception, webRequest,HttpStatus.INTERNAL_SERVER_ERROR.value(), INTERNAL_ERROR);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<String>> handleBadCredentialsException(BadCredentialsException exception, WebRequest webRequest) {
        return exceptionMessage(exception, webRequest, HttpStatus.UNAUTHORIZED.value(), exception.getMessage());

    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<String>> handleMalformedJwtException(ExpiredJwtException exception, WebRequest webRequest) {
        return exceptionMessage(exception, webRequest, HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
    }
private static ResponseEntity<ApiResponse<String>> exceptionMessage(Exception exception, WebRequest webRequest, int statusCode, String errorMessage){
    log.warn("{} {} occurred through {}", Exception.class.getSimpleName().toUpperCase(), exception.getMessage(), webRequest==null?"":webRequest.getContextPath());
        return ResponseEntity.ok(new ApiResponse<>(FAIL, statusCode, errorMessage));

}

}
