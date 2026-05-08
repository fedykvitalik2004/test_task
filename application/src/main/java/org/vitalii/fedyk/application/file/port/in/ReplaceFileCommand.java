package org.vitalii.fedyk.application.file.port.in;

import org.vitalii.fedyk.domain.file.model.FileName;
import org.vitalii.fedyk.domain.file.model.MediaType;

public record ReplaceFileCommand(FileName initialFileName, String content, FileName targetFileName) {
}
