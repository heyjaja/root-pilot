package com.root.pilot.exception.handler;

import com.root.pilot.exception.ErrorDetails;
import com.root.pilot.exception.OAuth2AuthenticationProcessingException;
import com.root.pilot.exception.TestExceptionResponse;
import com.root.pilot.exception.TestException;
import com.root.pilot.exception.TokenException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {

        ObjectError error = ex.getAllErrors().get(0);

        ErrorDetails errorDetails =
            new ErrorDetails("Validation Failed", error.getDefaultMessage(), ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenException.class)
    public final ResponseEntity<ErrorDetails> handleTokenException(TokenException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
            "Token Error", ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<TestExceptionResponse> handleUserNotFoundException(UsernameNotFoundException ex,
        WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
            "user not found", ex.getMessage(), request.getDescription(false));

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(new PrintWriter(pw));
        String stacktrace = sw.toString(); // stack trace as a string

        String exceptionClass = ex.getClass().toString();
        String location = request.getDescription(false);

        TestExceptionResponse exceptionMessage =
            TestExceptionResponse.builder()
                .message(ex.getMessage())
                .apiVersion("v1")
                .exception(exceptionClass.substring(exceptionClass.lastIndexOf(".") + 1))
                .client(request.getHeader("user-agent"))
                .location(location.substring(location.indexOf("=") + 1))
                .service("POKER_PASS")
                .serviceType("WEB")
                .logLevel("ERROR")
                .stackTrace(stacktrace)
                .build();

        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OAuth2AuthenticationProcessingException.class)
    public final ResponseEntity<ErrorDetails> handleOAuth2AuthenticationProcessingException(OAuth2AuthenticationProcessingException ex,
        WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
            "user not found", ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TestException.class)
    public ResponseEntity<TestExceptionResponse> exceptionTest(
        TestException ex, WebRequest request) {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(new PrintWriter(pw));
        String stacktrace = sw.toString(); // stack trace as a string

        TestExceptionResponse exception =
            new TestExceptionResponse(ex.getMessage(), request.getHeader("user-agent"), request.getDescription(false), stacktrace);

        TestExceptionResponse exceptionMessage =
            TestExceptionResponse.builder()
                .message(ex.getMessage())
                .apiVersion("v1")
                .exception(ex.getClass().toString())
                .client(request.getHeader("user-agent"))
                .location(request.getDescription(false))
                .service("POKER_PASS")
                .serviceType("WEB")
                .logLevel("ERROR")
                .stackTrace(stacktrace)
                .build();
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }
}
