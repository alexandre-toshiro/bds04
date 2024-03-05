package com.devsuperior.bds04.controller.exceptions;

import java.time.Instant;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.bds04.services.exception.ResourceNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		StandardError err = new StandardError();
		HttpStatus notFound = HttpStatus.NOT_FOUND;
		err.setTimestamp(Instant.now());
		err.setStatus(notFound.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(notFound).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		ValidationError err = new ValidationError();
		HttpStatus unprocessableEntity = HttpStatus.UNPROCESSABLE_ENTITY;
		err.setTimestamp(Instant.now());
		err.setStatus(unprocessableEntity.value());
		err.setError("Validation exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());


		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		fieldErrors.forEach(f -> {
			err.addError(f.getField(), f.getDefaultMessage());
		});

		return ResponseEntity.status(unprocessableEntity).body(err);
	}

}
