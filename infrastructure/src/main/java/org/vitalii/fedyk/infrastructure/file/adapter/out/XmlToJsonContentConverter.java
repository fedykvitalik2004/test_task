package org.vitalii.fedyk.infrastructure.file.adapter.out;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.domain.file.exception.ConversionException;
import org.vitalii.fedyk.domain.file.model.MediaType;

import static org.vitalii.fedyk.domain.file.model.MediaType.JSON;
import static org.vitalii.fedyk.domain.file.model.MediaType.XML;

@Component
public class XmlToJsonContentConverter implements ContentConverter {
  private final XmlMapper xmlMapper = new XmlMapper();
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convert(final String content) {
    try {
      final Object object = this.xmlMapper.readValue(content, Object.class);
        return this.objectMapper.writeValueAsString(object);
    } catch (Exception e) {
      throw new ConversionException("Cannot convert XML content to JSON format");
    }
  }

  @Override
  public boolean supports(final MediaType from, final MediaType to) {
    return XML.equals(from) && JSON.equals(to);
  }
}
