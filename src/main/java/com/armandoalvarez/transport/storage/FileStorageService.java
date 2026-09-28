package com.armandoalvarez.transport.storage;

import com.armandoalvarez.transport.exception.BusinessRuleViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final Set<String> ALLOWED_PDF = Set.of("application/pdf");
    private static final Set<String> ALLOWED_IMAGE = Set.of("image/png", "image/jpeg");
    private final Path root = Paths.get("uploads");

    public String storeDocument(MultipartFile file) {
        return store(file, ALLOWED_PDF, "Only PDF files are allowed");
    }

    public String storeImage(MultipartFile file) {
        return store(file, ALLOWED_IMAGE, "Only PNG or JPG images are allowed");
    }

    private String store(MultipartFile file, Set<String> allowedTypes, String errorMessage) {
        if (!allowedTypes.contains(file.getContentType())) {
            throw new BusinessRuleViolationException(errorMessage);
        }
        try {
            Files.createDirectories(root);
            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path destination = root.resolve(filename);
            file.transferTo(destination);
            return destination.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}