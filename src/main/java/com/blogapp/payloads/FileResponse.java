package com.blogapp.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class FileResponse {
    private String message;
    private String fileName;
}
