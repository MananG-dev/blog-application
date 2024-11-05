package com.blogapp.controllers;

import com.blogapp.Services.impl.FileServiceImpl;
import com.blogapp.payloads.FileResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

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

    @SneakyThrows
    @GetMapping(value = "/download", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @RequestParam(name="image") String fileName,
            HttpServletResponse httpServletResponse
    )   {
        InputStream is = fileService.getImage(path, fileName);
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(is, httpServletResponse.getOutputStream());
    }
}
