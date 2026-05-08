package org.vitalii.fedyk.domain.file.port.in;

public interface UploadFileUseCase {
  void process(String fileName, byte[] content);
}
