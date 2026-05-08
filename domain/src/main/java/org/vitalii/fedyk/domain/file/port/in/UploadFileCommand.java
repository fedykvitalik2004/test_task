package org.vitalii.fedyk.domain.file.port.in;

import org.vitalii.fedyk.domain.file.model.FileName;
import org.vitalii.fedyk.domain.file.model.MediaType;

public record UploadFileCommand(FileName initialFileName, String content, MediaType to) {
}
