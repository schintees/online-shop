package ro.msg.learning.shop.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
@RequestMapping
public class ApplicationExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class, ExportEntitiesNotFoundException.class})
    ResponseEntity<ErrorResponse> entityNotFoundHandler(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OutOfStockException.class)
    ResponseEntity<ErrorResponse> badRequestHandler(OutOfStockException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<ErrorResponse> badRequestHandler(Exception e) {
        return new ResponseEntity<>(new ErrorResponse("Constraint violation"), HttpStatus.CONFLICT);
    }

}
