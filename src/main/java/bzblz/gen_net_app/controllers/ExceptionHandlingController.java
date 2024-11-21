package bzblz.gen_net_app.controllers;

import bzblz.gen_net_app.exceptions.AlreadyExistsException;
import bzblz.gen_net_app.exceptions.AppException;
import bzblz.gen_net_app.exceptions.UnexpectedRequestException;
import javassist.NotFoundException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlingController {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundException(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> mismatchException(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> alreadyExistsException(AlreadyExistsException exception) {
        System.out.println("alreadyExistsException");
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(exception.getMessage()));
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> authenticationException(AuthenticationException exception) {
        System.out.println("authenticationException");
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorMessage(exception.getMessage()));
    }
    @ExceptionHandler(UnexpectedRequestException.class)
    public ResponseEntity<ErrorMessage> unexpectedRequestException(UnexpectedRequestException exception) {
        System.out.println("unexpectedRequestException");
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorMessage(exception.getMessage()));
    }
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorMessage> appException(AppException exception) {
        System.out.println("appException");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
