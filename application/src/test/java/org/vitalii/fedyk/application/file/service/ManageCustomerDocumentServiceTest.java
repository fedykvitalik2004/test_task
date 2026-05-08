package org.vitalii.fedyk.application.file.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vitalii.fedyk.domain.file.exception.FileAlreadyExistsException;
import org.vitalii.fedyk.domain.file.exception.FileStorageException;
import org.vitalii.fedyk.domain.file.model.Content;
import org.vitalii.fedyk.domain.file.model.FileDocument;
import org.vitalii.fedyk.domain.file.model.FileName;
import org.vitalii.fedyk.domain.file.model.MediaType;
import org.vitalii.fedyk.domain.file.port.in.UploadFileCommand;
import org.vitalii.fedyk.domain.file.port.out.ContentConverterFactoryPort;
import org.vitalii.fedyk.domain.file.port.out.ContentConverterPort;
import org.vitalii.fedyk.domain.file.port.out.FileStoragePort;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManageCustomerDocumentServiceTest {

  @Mock
  private ContentConverterFactoryPort converterFactory;

  @Mock
  private FileStoragePort storage;

  @Mock
  private ContentConverterPort converter;

  @InjectMocks
  private ManageCustomerDocumentService service;

  @Nested
  class UploadTests {

    @Test
    void upload_ShouldConvertAndSave_WhenFileDoesNotExist() {
      // Given
      final FileName initialName = FileName.parse("customer1_report_2026-05-08.xml");
      final String content = "<xml>data</xml>";
      final MediaType targetType = MediaType.JSON;
      final UploadFileCommand command = new UploadFileCommand(initialName, content, targetType);

      final FileName expectedTargetName = initialName.withMediaType(targetType);
      final String convertedJson = "{\"data\":\"json\"}";

      when(storage.exists(expectedTargetName)).thenReturn(false);
      when(converterFactory.getConverter(initialName.mediaType(), targetType)).thenReturn(converter);
      when(converter.convert(content)).thenReturn(convertedJson);

      // When
      final FileDocument result = service.upload(command);

      // Then
      assertThat(result.name()).isEqualTo(expectedTargetName);
      assertThat(result.content().value()).isEqualTo(convertedJson);

      verify(storage).overwrite(result);
    }

    @Test
    void upload_ShouldThrowException_WhenFileAlreadyExists() {
      // Given
      final FileName initialName = FileName.parse("customer1_report_2026-05-08.xml");
      final UploadFileCommand command = new UploadFileCommand(initialName, "Content", MediaType.JSON);
      final FileName targetName = initialName.withMediaType(MediaType.JSON);

      when(storage.exists(targetName)).thenReturn(true);

      // When and then
      assertThatThrownBy(() -> service.upload(command))
              .isInstanceOf(FileAlreadyExistsException.class)
              .hasMessageContaining("already exists");

      verify(storage).exists(targetName);
    }
  }

  @Nested
  class DeleteTests {

    @Test
    void delete_ShouldInvokeStorageDelete() {
      // Given
      final FileName targetFileName = FileName.parse("customer1_report_2026-05-08.json");

      // When
      service.delete(targetFileName);

      // Then
      verify(storage).delete(targetFileName);
    }

    @Test
    void delete_ShouldThrowException_WhenStorageFails() {
      // Given
      final FileName targetFileName = FileName.parse("clientA_invoice_2026-05-08.json");
      doThrow(new RuntimeException("Storage access error"))
              .when(storage).delete(targetFileName);

      // When and then
      assertThatThrownBy(() -> service.delete(targetFileName))
              .isInstanceOf(RuntimeException.class)
              .hasMessage("Storage access error");
    }
  }

  @Nested
  class GetFileTests {

    @Test
    void getFile_ReturnsDocument_WhenExists() {
      // Given
      final FileName fileName = FileName.parse("client_report_2026-05-08.json");
      final Content expectedContent = new Content("{\"status\":\"ok\"}");

      when(storage.readContent(fileName)).thenReturn(Optional.of(expectedContent));

      // When
      final FileDocument result = service.getFile(fileName);

      // Then
      assertThat(result).isNotNull();
      assertThat(result.name().toFullString()).isEqualTo(fileName.toFullString());
      assertThat(result.content()).isEqualTo(expectedContent);
      verify(storage).readContent(fileName);
    }

    @Test
    void getFile_ThrowsException_WhenNotFound() {
      // Given
      final FileName fileName = FileName.parse("unknown_file_2026-05-08.xml");
      when(storage.readContent(fileName)).thenReturn(Optional.empty());

      // When & Then
      assertThatThrownBy(() -> service.getFile(fileName))
              .isInstanceOf(FileStorageException.class)
              .hasMessage("Could not read a file content");
    }
  }
}