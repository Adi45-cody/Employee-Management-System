package com.example.demo.exception;

public class InvalidFileTypeException extends RuntimeException {

	public InvalidFileTypeException(String message) {
		super(message);
	}
}
