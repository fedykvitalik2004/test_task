package org.vitalii.fedyk.domain.file.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record FileName(String customer, String type, String date, MediaType mediaType) {
  private static final Pattern FILENAME_PATTERN =
          Pattern.compile("^([a-zA-Z0-9]+)_([a-zA-Z0-9]+)_(\\d{4}-\\d{2}-\\d{2})\\.([a-z0-9]+)$");

  public static FileName parse(final String fileNameWithExtension) {
    if (fileNameWithExtension == null) {
      throw new IllegalArgumentException("File name cannot be null");
    }

    final Matcher matcher = FILENAME_PATTERN.matcher(fileNameWithExtension);
    if (!matcher.matches()) {
      throw new IllegalArgumentException(
              "Invalid file name format. Expected: customer_type_yyyy-MM-dd.ext"
      );
    }

    return new FileName(
            matcher.group(1),
            matcher.group(2),
            matcher.group(3),
            MediaType.fromExtension(matcher.group(4))
    );
  }

  public FileName withMediaType(MediaType newMediaType) {
    return new FileName(this.customer, this.type, this.date, newMediaType);
  }

  public String toFullString() {
    return String.format("%s_%s_%s.%s", customer, type, date, mediaType.getExtension());
  }
}
