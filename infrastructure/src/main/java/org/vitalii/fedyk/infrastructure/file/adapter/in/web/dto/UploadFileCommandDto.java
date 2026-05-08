package org.vitalii.fedyk.infrastructure.file.adapter.in.web.dto;

import org.springframework.web.multipart.MultipartFile;
import org.vitalii.fedyk.domain.file.model.MediaType;

public record UploadFileCommandDto(MultipartFile initialFileName, String to) {
}
