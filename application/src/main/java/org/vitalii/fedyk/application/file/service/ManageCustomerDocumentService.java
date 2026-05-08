package org.vitalii.fedyk.application.file.service;

import org.vitalii.fedyk.domain.file.port.in.DeleteFileByNameUseCase;
import org.vitalii.fedyk.domain.file.port.in.GetDocumentUseCase;
import org.vitalii.fedyk.domain.file.port.in.ReplaceFileCommand;
import org.vitalii.fedyk.domain.file.port.in.SearchCriteria;
import org.vitalii.fedyk.domain.file.port.in.UploadFileCommand;
import org.vitalii.fedyk.domain.file.port.in.UploadCustomerDataUseCase;
import org.vitalii.fedyk.domain.file.exception.FileAlreadyExistsException;
import org.vitalii.fedyk.domain.file.exception.FileStorageException;
import org.vitalii.fedyk.domain.file.model.Content;
import org.vitalii.fedyk.domain.file.model.FileDocument;
import org.vitalii.fedyk.domain.file.model.FileName;
import org.vitalii.fedyk.domain.file.port.out.ContentConverterFactoryPort;
import org.vitalii.fedyk.domain.file.port.out.ContentConverterPort;
import org.vitalii.fedyk.domain.file.port.out.FileStoragePort;

import java.util.List;

public class ManageCustomerDocumentService implements UploadCustomerDataUseCase, GetDocumentUseCase, DeleteFileByNameUseCase {
  private final ContentConverterFactoryPort converterFactory;
  private final FileStoragePort storage;

  public ManageCustomerDocumentService(ContentConverterFactoryPort converterFactory, FileStoragePort fileStoragePort) {
    this.converterFactory = converterFactory;
    this.storage = fileStoragePort;
  }

  @Override
  public FileDocument upload(final UploadFileCommand command) {
    // Parse acts like a validator
    final FileName initialFileName = command.initialFileName();
    final FileName targetFileName = initialFileName
            .withMediaType(command.to());

    final boolean fileExists = this.storage.exists(targetFileName);
    if (fileExists) {
      throw new FileAlreadyExistsException("File '" + targetFileName.toFullString() + "' already exists");
    }

    // It can return an exception if there is no such converter
    final ContentConverterPort contentConverterPort = this.converterFactory.getConverter(initialFileName.mediaType(), command.to());
    final String convertedContent = contentConverterPort.convert(command.content());

    final FileDocument document = new FileDocument(targetFileName, new Content(convertedContent));

    this.storage.overwrite(document);

    return document;
  }

  // Task 2
  @Override
  public void replace(final ReplaceFileCommand command) {
    // Parse acts like a validator
    final FileName initialFileName = command.initialFileName();
    final FileName targetFileName = command.targetFileName();

    final ContentConverterPort contentConverterPort =
            this.converterFactory.getConverter(initialFileName.mediaType(), targetFileName.mediaType());

    // May throw an exception on validation failure or missing converter
    final String convertedContent = contentConverterPort.convert(command.content());

    final FileDocument document = new FileDocument(targetFileName, new Content(convertedContent));

    this.storage.overwrite(document);
  }

  //Task 3
  @Override
  public void delete(final FileName targetFileName) {
    this.storage.delete(targetFileName);
  }

  // Task 4
  @Override
  public FileDocument getFile(final FileName fileName) {
    final Content content = this.storage.readContent(fileName)
            .orElseThrow(() -> new FileStorageException("Could not read a file content"));
    return new FileDocument(fileName, content);
  }

  // Tasks 5, 6, 7
  @Override
  public List<FileName> search(final SearchCriteria criteria) {
    return this.storage.listAllFileNames().stream()
            .filter(fileName -> this.matches(criteria, fileName))
            .toList();
  }

  public boolean matches(final SearchCriteria criteria, final FileName fileName) {
    return matches(criteria.customer(), fileName.customer()) &&
           matches(criteria.type(), fileName.type()) &&
           matches(criteria.date(), fileName.date());
  }

  private <T> boolean matches(final T criterionValue, final T fileNameValue) {
    if (criterionValue == null) {
      return true;
    }
    return fileNameValue.equals(criterionValue);
  }


}