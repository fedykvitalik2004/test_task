package org.vitalii.fedyk.domain.file.port.in;

import org.vitalii.fedyk.domain.file.model.FileDocument;
import org.vitalii.fedyk.domain.file.model.FileName;

import java.util.List;

public interface GetDocumentUseCase {
  FileDocument getFile(FileName targetFileName);

  List<FileName> search(SearchCriteria criteria);
}
