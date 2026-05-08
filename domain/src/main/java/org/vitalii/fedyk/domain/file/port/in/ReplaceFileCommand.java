package org.vitalii.fedyk.domain.file.port.in;

import org.vitalii.fedyk.domain.file.model.FileName;

public record ReplaceFileCommand(FileName initialFileName, String content, FileName targetFileName) {
}
