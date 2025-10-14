package com.example.demo.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class) //as input this exception contains information about which fields failed
	public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex){ //JSON response with error details
		
		Map<String, Object> errors = new HashMap<>(); //Creates a map to store error information like status code, timestamp, and messages
		
		errors.put("status", 400); //Adds a HTTP status code 400 (Bad Request) to the map
		errors.put("timestamp", LocalDateTime.now()); //Adds the current timestamp to show when the error occurred
		
		List<String> validationErrors = ex.getBindingResult().getFieldErrors().stream().map(err -> err.getDefaultMessage()).toList();
		
		/*ex.getBindingResult().getFieldErrors() → gets all field-level errors from the validation
		 .stream() → converts the list to a stream for functional operations
		 .map(err -> err.getDefaultMessage()) → extracts the human-readable error message for each field
		 .toList() → converts the stream back to a list of strings
		*/
		errors.put("errors", validationErrors); //Adds the list of validation error messages to the map under the key "errors"
		
		
		return ResponseEntity.badRequest().body(errors); //Returns an HTTP 400 Bad Request response with the errors map as JSON
		
	}
	
	/*@ExceptionHandler(RuntimeException.class) //Handles any RuntimeException thrown in the application
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) { 
        Map<String, Object> error = new HashMap<>(); //Creates a new map for runtime errors Adds status 404 (Not Found)
        error.put("status", 404);
        error.put("timestamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        return ResponseEntity.status(404).body(error); //Returns HTTP 404 Not Found response with the error map as JSON 
        //.status(404) sets the HTTP status.
        //.body(error) sets the response content.
    }*/
	
	// Invalid file type
    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidFileType(InvalidFileTypeException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", 415); // 415 Unsupported Media Type
        error.put("timestamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        return ResponseEntity.status(415).body(error);
    }
    
 // File too large
    @ExceptionHandler(FileSizeExceededException.class)
    public ResponseEntity<Map<String, Object>> handleFileSize(FileSizeExceededException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", 413); // 413 Payload Too Large
        error.put("timestamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        return ResponseEntity.status(413).body(error);
    }
    
 // Generic Runtime exception
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", 500);
        error.put("timestamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        return ResponseEntity.status(500).body(error);
    }
	
}//end of class
