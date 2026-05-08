package org.vitalii.fedyk.domain.file.port.out;

import org.vitalii.fedyk.domain.file.model.Content;
import org.vitalii.fedyk.domain.file.model.FileDocument;
import org.vitalii.fedyk.domain.file.model.FileName;

import java.util.List;
import java.util.Optional;

public interface FileStoragePort {
  void overwrite(FileDocument fileDocument);

  Optional<Content> readContent(FileName fileName);

  List<FileName> listAllFileNames();

  void delete(FileName fileName);

  boolean exists(FileName fileName);
}
