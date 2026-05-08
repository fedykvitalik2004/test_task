package org.vitalii.fedyk.domain.file.exception;

public class FileAlreadyExistsException extends RuntimeException{
  public FileAlreadyExistsException(String message) {
    super(message);
  }
}
