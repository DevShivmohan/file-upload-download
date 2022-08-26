package com.example.file.controller;

import com.example.file.exception.GenericException;
import com.example.file.model.FileRequestDTO;
import com.example.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/upload")
    public ResponseEntity<?> uploadFile(@RequestPart(value = "file") final MultipartFile multipartFile) throws IOException, GenericException {
        log.info("upload api hits");
        return fileService.uploadFile(multipartFile);
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(FileRequestDTO fileRequestDTO, HttpServletRequest request) throws IOException, GenericException {
        log.info("Download file api hits");
        return fileService.downloadFile(fileRequestDTO,request);
    }
}
