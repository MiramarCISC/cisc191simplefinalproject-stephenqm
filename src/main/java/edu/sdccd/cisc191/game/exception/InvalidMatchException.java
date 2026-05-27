package edu.sdccd.cisc191.game.exception;


// MODULE 4: Exceptions, File I/O, Database Persistence
// Custom unchecked exception thrown when match input is invalid
public class InvalidMatchException extends RuntimeException {

    // Basic constructor with just a message
    public InvalidMatchException(String message) {
        super(message);
    }

    // Constructor with message + root cause (for wrapping other exceptions)
    public InvalidMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}