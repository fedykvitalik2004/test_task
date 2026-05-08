package org.vitalii.fedyk.infrastructure.adapter.out.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.domain.file.exception.UnsupportedConversionException;
import org.vitalii.fedyk.domain.file.model.MediaType;
import org.vitalii.fedyk.domain.file.port.out.ContentConverterFactoryPort;
import org.vitalii.fedyk.domain.file.port.out.ContentConverterPort;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContentConverterFactoryAdapter implements ContentConverterFactoryPort {
  private final List<ContentConverter> contentConverters;

  @Override
  public ContentConverterPort getConverter(final MediaType from, final MediaType to) {
    return this.contentConverters.stream()
            .filter(converter -> converter.supports(from, to))
            .findFirst()
            .orElseThrow(() -> new UnsupportedConversionException(from, to));
  }
}
