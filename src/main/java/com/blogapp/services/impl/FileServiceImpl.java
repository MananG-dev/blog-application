package com.blogapp.services.impl;

import com.blogapp.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(String path, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = path + File.separator + fileName;

        File pathFile = new File(path);
        if (!pathFile.exists()) {
            pathFile.mkdir();
        }
        try {
            Files.copy(file.getInputStream(), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

    @Override
    public InputStream downloadFile(String path, String file) {
        String filePath = path + File.separator + file;
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return is;
    }
}
