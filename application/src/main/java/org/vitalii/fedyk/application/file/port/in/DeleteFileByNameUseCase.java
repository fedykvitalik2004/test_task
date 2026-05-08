package org.vitalii.fedyk.application.file.port.in;

import org.vitalii.fedyk.domain.file.model.FileName;

public interface DeleteFileByNameUseCase {
  void delete(FileName targetFileName);
}
