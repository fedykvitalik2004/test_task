package org.vitalii.fedyk.domain.file.model;

public enum MediaType {
  XML("xml"), JSON("json");

  private final String extension;

  MediaType(final String extension) {
    this.extension = extension;
  }

  public String getExtension() {
    return extension;
  }

  public static MediaType fromExtension(String extension) {
    try {
      return MediaType.valueOf(extension.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Unsupported file format '" + extension + "'");
    }
  }
}
