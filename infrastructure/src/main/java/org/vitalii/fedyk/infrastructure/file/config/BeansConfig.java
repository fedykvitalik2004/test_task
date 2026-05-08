package org.vitalii.fedyk.infrastructure.file.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vitalii.fedyk.application.file.port.in.DeleteFileByNameUseCase;
import org.vitalii.fedyk.application.file.port.in.GetDocumentUseCase;
import org.vitalii.fedyk.application.file.port.in.UploadCustomerDataUseCase;
import org.vitalii.fedyk.application.file.service.ManageCustomerDocumentService;
import org.vitalii.fedyk.domain.file.port.out.ContentConverterFactoryPort;
import org.vitalii.fedyk.domain.file.port.out.FileStoragePort;
import org.vitalii.fedyk.infrastructure.file.adapter.out.ContentConverterFactoryAdapter;
import org.vitalii.fedyk.infrastructure.file.adapter.out.LocalFileStorageAdapter;

@Configuration
public class BeansConfig {
  @Bean
  public UploadCustomerDataUseCase uploadCustomerDataUseCase(final ContentConverterFactoryPort converterFactory, final FileStoragePort fileStoragePort) {
    return new ManageCustomerDocumentService(converterFactory, fileStoragePort);
  }

  @Bean
  public GetDocumentUseCase getDocumentUseCase(final ContentConverterFactoryPort converterFactory, final FileStoragePort fileStoragePort) {
    return new ManageCustomerDocumentService(converterFactory, fileStoragePort);
  }

  @Bean
  public DeleteFileByNameUseCase deleteFileByNameUseCase(final ContentConverterFactoryPort converterFactory, final FileStoragePort fileStoragePort) {
    return new ManageCustomerDocumentService(converterFactory, fileStoragePort);
  }

}
