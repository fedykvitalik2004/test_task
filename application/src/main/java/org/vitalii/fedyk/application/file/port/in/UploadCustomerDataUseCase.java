package org.vitalii.fedyk.application.file.port.in;

import org.vitalii.fedyk.domain.file.model.FileDocument;

public interface UploadCustomerDataUseCase {
  FileDocument upload(UploadFileCommand command);

  void replace(ReplaceFileCommand command);
}
