package exceptions;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandel {
@ExceptionHandler (EmptyResultDataAccessException.class)
public ResponseEntity<String> handleEmptyResultDataAccessException(EmptyResultDataAccessException exception){
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found " + "Error "+exception.getMessage());
}

}
