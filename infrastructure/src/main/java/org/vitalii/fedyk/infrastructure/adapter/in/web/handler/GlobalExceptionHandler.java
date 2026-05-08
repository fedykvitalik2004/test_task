package org.vitalii.fedyk.infrastructure.adapter.in.web.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.vitalii.fedyk.domain.file.exception.ConversionException;
import org.vitalii.fedyk.domain.file.exception.FileAlreadyExistsException;
import org.vitalii.fedyk.domain.file.exception.FileStorageException;
import org.vitalii.fedyk.domain.file.exception.UnsupportedConversionException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler({IllegalArgumentException.class, FileStorageException.class, ConversionException.class,
          FileAlreadyExistsException.class, UnsupportedConversionException.class, IllegalStateException.class})
  @ResponseBody
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponseDto handleRuntimeException(final RuntimeException exception) {
    return new ErrorResponseDto(exception.getMessage());
  }
}
