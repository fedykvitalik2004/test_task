package org.vitalii.fedyk.domain.file.port.in;

import org.vitalii.fedyk.domain.file.model.FileName;

public interface DeleteFileByNameUseCase {
  void delete(FileName targetFileName);
}
