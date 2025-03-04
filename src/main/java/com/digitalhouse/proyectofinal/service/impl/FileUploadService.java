package com.digitalhouse.proyectofinal.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final String UPLOAD_DIR = "src/main/resources/static/images";
    private final ObjectMapper objectMapper;

    public String uploadFiles(List<MultipartFile> files) throws IOException {

        List<String> fileNames = new ArrayList<>();

        if (files != null) {
            for (MultipartFile file : files) {
                String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR, uniqueFileName);

                Files.copy(file.getInputStream(), path);
                fileNames.add("http://localhost:8181/images/" + uniqueFileName);
            }
        }

        return objectMapper.writeValueAsString(fileNames);

    }

}