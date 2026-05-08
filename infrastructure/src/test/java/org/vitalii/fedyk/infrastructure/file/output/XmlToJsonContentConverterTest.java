package org.vitalii.fedyk.infrastructure.file.output;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.vitalii.fedyk.domain.file.port.out.ContentConverterPort;
import org.vitalii.fedyk.infrastructure.file.adapter.out.XmlToJsonContentConverter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

class XmlToJsonContentConverterTest {
  private final ContentConverterPort adapter = new XmlToJsonContentConverter();

  @Test
  @SneakyThrows
  void convert() {
    // Given
    final Path xmlInputPath = Path.of("src/test/resources/input/clients.xml");
    final String input = Files.readString(xmlInputPath);

    // When
    final String actual = this.adapter.convert(input);

    // Then
    final var expectedPath = Path.of("src/test/resources/expected/clients.json");
    final var expected = Files.readString(expectedPath);

    assertEquals(expected, actual, true);
  }
}