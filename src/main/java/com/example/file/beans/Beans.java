package com.example.file.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class Beans {

    @Bean
    public Path getServerDownloadPath(){
        File file= new File(System.getProperty("user.dir")+ File.separator+"file_uploads");
        file.mkdirs();
        System.out.println(file.getAbsolutePath());
        return Paths.get(file.getAbsolutePath());
    }
}
