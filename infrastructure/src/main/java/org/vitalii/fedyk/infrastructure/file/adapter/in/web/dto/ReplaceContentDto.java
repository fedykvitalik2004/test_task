package org.vitalii.fedyk.infrastructure.file.adapter.in.web.dto;

import org.springframework.web.multipart.MultipartFile;

public record ReplaceContentDto(MultipartFile initialFileName) {
}
