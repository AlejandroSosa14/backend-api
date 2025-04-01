package com.digitalhouse.proyectofinal.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    private final ObjectMapper objectMapper;
    @Value("${url.backend}")
    private String urlBackend;

    public String uploadFiles(String dir, List<MultipartFile> files) throws IOException {

        List<String> fileNames = new ArrayList<>();

        if (files != null) {
            for (MultipartFile file : files) {
                String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + dir, uniqueFileName);

                Files.copy(file.getInputStream(), path);
                fileNames.add(urlBackend + "/uploads/" + dir + uniqueFileName);
            }
        }

        return objectMapper.writeValueAsString(fileNames);

    }

}