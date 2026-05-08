package org.vitalii.fedyk.domain.file.port.in;

import org.vitalii.fedyk.domain.file.model.MediaType;

public record UpdateFileContentCommand(String fileName, String content, MediaType to) {
}
