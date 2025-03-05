package com.digitalhouse.proyectofinal.controller;

import com.digitalhouse.proyectofinal.service.impl.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UploadFileController {

    private final FileUploadService fileUploadService;

    @PostMapping(value = "/api/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> handleFileUpload(@RequestParam("files") List<MultipartFile> files) throws IOException {
        try {
            String fileNames = fileUploadService.uploadFiles(files);
            return ResponseEntity.ok(fileNames);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload error: " + e.getMessage());
        }
    }
}
