package com.blogapp.Services.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.blogapp.Services.FileService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // get file name
        String fileName = file.getOriginalFilename();

        String randomId = UUID.randomUUID().toString();
        String randomFileName = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));
        String filePath = path + File.separator + randomFileName;

        File file1 = new File(path);
        if(!file1.exists()) {
            file1.mkdir();
        }
        Files.copy(file.getInputStream(), Path.of(filePath));
        return fileName;

    }

    @Override
    public InputStream getImage(String path, String fileName) {
        String filePath = path + File.separator + fileName;
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return is;
    }

}
