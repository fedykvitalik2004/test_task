package org.vitalii.fedyk.infrastructure.adapter.out.converter;

import org.vitalii.fedyk.domain.file.model.MediaType;
import org.vitalii.fedyk.domain.file.port.out.ContentConverterPort;

public interface ContentConverter extends ContentConverterPort {
  boolean supports(MediaType from, MediaType to);
}
