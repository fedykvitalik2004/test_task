package org.vitalii.fedyk.infrastructure.file.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.vitalii.fedyk.application.file.port.in.DeleteFileByNameUseCase;
import org.vitalii.fedyk.application.file.port.in.GetDocumentUseCase;
import org.vitalii.fedyk.application.file.port.in.ReplaceFileCommand;
import org.vitalii.fedyk.application.file.port.in.SearchCriteria;
import org.vitalii.fedyk.application.file.port.in.UploadCustomerDataUseCase;
import org.vitalii.fedyk.application.file.port.in.UploadFileCommand;
import org.vitalii.fedyk.domain.file.model.FileDocument;
import org.vitalii.fedyk.domain.file.model.FileName;
import org.vitalii.fedyk.domain.file.model.MediaType;
import org.vitalii.fedyk.infrastructure.file.adapter.in.web.dto.FileDocumentDto;
import org.vitalii.fedyk.infrastructure.file.adapter.in.web.dto.FileNameDto;
import org.vitalii.fedyk.infrastructure.file.adapter.in.web.dto.ReplaceContentDto;
import org.vitalii.fedyk.infrastructure.file.adapter.in.web.dto.SearchCriteriaDTO;
import org.vitalii.fedyk.infrastructure.file.adapter.in.web.dto.UploadFileCommandDto;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor
public class FileController {
  private final DeleteFileByNameUseCase deleteFileByNameUseCase;

  private final GetDocumentUseCase getDocumentUseCase;

  private final UploadCustomerDataUseCase uploadCustomerDataUseCase;

  // Task 1
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @SneakyThrows
  public FileDocumentDto upload(@ModelAttribute final UploadFileCommandDto request) {
    final String content = new String(request.initialFileName().getBytes(), StandardCharsets.UTF_8);

    final UploadFileCommand command = new UploadFileCommand(
            FileName.parse(request.initialFileName().getOriginalFilename()),
            content,
            MediaType.fromExtension(request.to()));

    final FileDocument fileDocument = this.uploadCustomerDataUseCase.upload(command);
    return FileDocumentDto
            .builder()
            .targetFileName(fileDocument.name().toFullString())
            .content(fileDocument.content().value())
            .build();
  }

  // Task 2
  @PutMapping(value = "/{targetFileName}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @SneakyThrows
  public void replace(@PathVariable final String targetFileName, @ModelAttribute final ReplaceContentDto request) {
    final String content = new String(request.initialFileName().getBytes(), StandardCharsets.UTF_8);

    final ReplaceFileCommand command = new ReplaceFileCommand(
            FileName.parse(request.initialFileName().getOriginalFilename()),
            content,
            FileName.parse(targetFileName));

    this.uploadCustomerDataUseCase.replace(command);
  }

  // Task 3
  @DeleteMapping("/{targetFileName}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable final String targetFileName) {
    final FileName name = FileName.parse(targetFileName);
    this.deleteFileByNameUseCase.delete(name);
  }

  // Task 4
  @GetMapping("/{targetFileName}")
  public FileDocumentDto getFile(@PathVariable String targetFileName) {
    final FileDocument fileDocument = this.getDocumentUseCase.getFile(FileName.parse(targetFileName));
    return FileDocumentDto.builder()
            .targetFileName(fileDocument.name().toFullString())
            .content(fileDocument.content().value())
            .build();
  }

  // Tasks 5, 6, 7
  @PostMapping(value = "/search")
  public List<FileNameDto> search(@RequestBody final SearchCriteriaDTO searchCriteriaDTO) {
    final SearchCriteria searchCriteria = SearchCriteria.builder()
            .customer(searchCriteriaDTO.customer())
            .type(searchCriteriaDTO.type())
            .date(searchCriteriaDTO.date())
            .build();
    final List<FileName> fileNameList = this.getDocumentUseCase.search(searchCriteria);
    return fileNameList.stream()
            .map(name -> new FileNameDto(name.toFullString()))
            .toList();
  }
}
