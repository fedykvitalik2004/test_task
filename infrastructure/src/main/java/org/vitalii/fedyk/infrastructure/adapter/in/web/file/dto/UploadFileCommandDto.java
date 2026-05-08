package org.vitalii.fedyk.infrastructure.adapter.in.web.file.dto;

import org.springframework.web.multipart.MultipartFile;

public record UploadFileCommandDto(MultipartFile initialFileName, String to) {
}
