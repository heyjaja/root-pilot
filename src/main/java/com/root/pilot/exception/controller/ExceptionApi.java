package com.root.pilot.exception.controller;

import com.root.pilot.exception.TestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionApi {

    private final String[] messages = {
        "stamp on it",
        "christmas without you",
        "the magic of christmas time",
        "find me",
        "to the moon"
    };

    @GetMapping
    public ResponseEntity<?> getException(String message) {
        if(message.equals("hello")) {
            throw new TestException("test error");
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/{index}")
    public ResponseEntity<?> getMessage(@PathVariable int index) {
        if(index >= messages.length) {
            throw new TestException("메시지가 없습니다.");
        }
        return new ResponseEntity<>(messages[index], HttpStatus.OK);
    }
}
