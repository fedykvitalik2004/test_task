package org.vitalii.fedyk.infrastructure.file.adapter.out;

import org.vitalii.fedyk.domain.file.model.MediaType;
import org.vitalii.fedyk.domain.file.port.out.ContentConverterPort;

public interface ContentConverter extends ContentConverterPort {
  boolean supports(MediaType from, MediaType to);
}
