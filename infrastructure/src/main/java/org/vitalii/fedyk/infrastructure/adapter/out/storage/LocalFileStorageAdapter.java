package org.vitalii.fedyk.infrastructure.adapter.out.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.vitalii.fedyk.domain.file.exception.FileStorageException;
import org.vitalii.fedyk.domain.file.model.Content;
import org.vitalii.fedyk.domain.file.model.FileDocument;
import org.vitalii.fedyk.domain.file.model.FileName;
import org.vitalii.fedyk.domain.file.port.out.FileStoragePort;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class LocalFileStorageAdapter implements FileStoragePort {
  private final Path storageDirectory;

  public LocalFileStorageAdapter(@Value("${app.storage.path}") final String storagePath) {
    this.storageDirectory = Path.of(storagePath);
    this.initDirectory();
  }

  private void initDirectory() {
    try {
      // Check if the path exists and verify it is not a file
      if (Files.exists(this.storageDirectory) && !Files.isDirectory(this.storageDirectory)) {
        throw new IllegalStateException("Path '" + this.storageDirectory +
                                        "' already exists but it is a file, not a directory");
      }

      Files.createDirectories(this.storageDirectory);
    } catch (IOException e) {
      throw new RuntimeException("Failed to initialize file storage at " + this.storageDirectory, e);
    }
  }

  @Override
  public void overwrite(final FileDocument fileDocument) {
    try {
      final Path filePath = this.storageDirectory.resolve(fileDocument.name().toFullString());
      Files.writeString(
              filePath,
              fileDocument.content().value(),
              StandardCharsets.UTF_8,
              StandardOpenOption.CREATE,
              StandardOpenOption.TRUNCATE_EXISTING,
              StandardOpenOption.WRITE
      );
    } catch (IOException e) {
      throw new FileStorageException("Failed to overwrite file '" + fileDocument.name() + "'");
    }
  }

  @Override
  public Optional<Content> readContent(final FileName fileName) {
    try {
      return Optional.of(new Content(Files.readString(this.resolvePath(fileName.toFullString()))));
    } catch (IOException e) {
      return Optional.empty();
    }
  }

  @Override
  public void delete(final FileName fileName) {
    try {
      Files.delete(this.resolvePath(fileName.toFullString()));
    } catch (IOException e) {
      throw new FileStorageException("Failed to delete file " + fileName);
    }
  }

  @Override
  public List<FileName> listAllFileNames() {
    try (final Stream<Path> stream = Files.list(storageDirectory)) {
      return stream
              .filter(file -> !Files.isDirectory(file))
              .map(Path::getFileName)
              .map(Path::toString)
              .flatMap(name -> {
                try {
                  return Stream.of(FileName.parse(name));
                } catch (IllegalArgumentException | FileStorageException e) {
                  return Stream.empty();
                }
              })
              .toList();
    } catch (IOException e) {
      throw new FileStorageException("Failed to list files from storage");
    }
  }

  @Override
  public boolean exists(final FileName fileName) {
    return Files.exists(this.resolvePath(fileName.toFullString()));
  }

  private Path resolvePath(final String fileName) {
    return this.storageDirectory.resolve(fileName);
  }
}
