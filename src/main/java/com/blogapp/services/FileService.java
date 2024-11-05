package com.blogapp.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {
    String uploadFile(String path, MultipartFile file);
    InputStream downloadFile(String path, String file);
}
