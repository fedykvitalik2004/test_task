package org.vitalii.fedyk.infrastructure.file.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Builder;

@Builder
public record FileDocumentDto(String targetFileName, @JsonRawValue String content) {
}
