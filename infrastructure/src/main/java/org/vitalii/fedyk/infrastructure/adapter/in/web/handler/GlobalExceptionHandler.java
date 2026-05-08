package org.vitalii.fedyk.infrastructure.adapter.in.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(RuntimeException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleRuntimeException(final RuntimeException exception) {
    return new ErrorResponseDto(exception.getMessage());
  }
}
