package org.vitalii.fedyk.application.file.port.in;

import org.vitalii.fedyk.domain.file.model.MediaType;

public record UpdateFileContentCommand(String fileName, String content, MediaType to) {
}
