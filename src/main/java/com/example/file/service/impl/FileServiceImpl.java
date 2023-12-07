package com.example.file.service.impl;

import com.example.file.exception.GenericException;
import com.example.file.model.FileRequestDTO;
import com.example.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private Path path;

    @Override
    public ResponseEntity<?> uploadFile(final MultipartFile multipartFile) throws IOException, GenericException {
        if(multipartFile.isEmpty())
            throw new GenericException(HttpStatus.NOT_ACCEPTABLE.value(),"File cannot to be empty");
        log.info(multipartFile.getOriginalFilename());
        Files.copy(multipartFile.getInputStream(),path.resolve(multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
    }

    @Override
    public ResponseEntity<?> downloadFile(final FileRequestDTO fileRequestDTO, final HttpServletRequest request) throws IOException, GenericException {
        var resource=new UrlResource(path.resolve(fileRequestDTO.getFileName()).toUri());
        if(!(resource.exists() || resource.isReadable()))
            throw new GenericException(HttpStatus.NOT_FOUND.value(), "File does not exists");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.resolve(fileRequestDTO.getFileName()).toFile().getName() + "\"").body(resource);
    }

    @Override
    public void downloadFileByHttpOutputStream(FileRequestDTO fileRequestDTO, HttpServletResponse httpServletResponse) throws IOException, GenericException {
        httpServletResponse.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+path.resolve(fileRequestDTO.getFileName()).toFile().getName());
        try  {
            FileSystemResource resource = new FileSystemResource(path.resolve(fileRequestDTO.getFileName()).toFile());
            StreamUtils.copy(resource.getInputStream(), httpServletResponse.getOutputStream());
            httpServletResponse.getOutputStream().flush();
        } catch (Throwable e) {
            throw new GenericException(HttpStatus.NOT_FOUND.value(), "File not found");
        }
    }

    @Override
    public ResponseEntity<?> downloadFileByInputStreamResource(final FileRequestDTO fileRequestDTO) throws IOException, GenericException {
        InputStreamResource resource = new InputStreamResource(new FileInputStream(path.resolve(fileRequestDTO.getFileName()).toFile()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+path.resolve(fileRequestDTO.getFileName()).toFile().getName())
                .contentLength(path.resolve(fileRequestDTO.getFileName()).toFile().length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
