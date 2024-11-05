package com.blogapp.controllers;

import com.blogapp.Services.impl.FileServiceImpl;
import com.blogapp.payloads.FileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileServiceImpl fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam(name = "image") MultipartFile file) {
        String fileName = null;

        try {
            fileName = fileService.uploadImage(path, file);
        } catch (IOException e) {
            return new ResponseEntity<>(FileResponse.builder().fileName(null).message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(FileResponse.builder()
                .message("File uploaded successfully")
                .fileName(fileName)
                .build(), HttpStatus.OK);
    }
}
