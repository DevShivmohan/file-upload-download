package com.example.file.service;

import com.example.file.exception.GenericException;
import com.example.file.model.FileRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface FileService {
    ResponseEntity<?> uploadFile(final MultipartFile multipartFile) throws IOException, GenericException;
    ResponseEntity<?> downloadFile(final FileRequestDTO fileRequestDTO, final HttpServletRequest request) throws IOException, GenericException;
}
