package org.vitalii.fedyk.domain.file.port.out;

import org.vitalii.fedyk.domain.file.model.MediaType;

public interface ContentConverterFactoryPort {
  ContentConverterPort getConverter(MediaType from, MediaType to);
}
