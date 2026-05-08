package org.vitalii.fedyk.domain.file.exception;

import org.vitalii.fedyk.domain.file.model.MediaType;

public class UnsupportedConversionException extends RuntimeException {
  public UnsupportedConversionException(MediaType from, MediaType to) {
    super("No converter found for %s -> %s".formatted(from, to));
  }
}