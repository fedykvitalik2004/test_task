package org.vitalii.fedyk.infrastructure.adapter.in.web.file.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Builder;

@Builder
public record FileDocumentDto(String targetFileName, String content) {
}
