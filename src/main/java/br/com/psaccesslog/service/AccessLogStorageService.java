package br.com.psaccesslog.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

@Service
public class FileSystemStorageService  {

    @Value("${storeUpload.path}")
    private String storeUploadPath;

//    public FileSystemStorageService() throws IOException {
//        String strPath = getPath();
//        rootLocation = Paths.get(strPath);
//    }

    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                throw new RuntimeException("Cannot store file with relative path outside current directory " + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, Paths.get(storeUploadPath).resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
    }


    public Path load(String filename) {
//        return rootLocation.resolve(filename);
        return Paths.get(storeUploadPath).resolve(filename);
    }

    public String getPath() throws IOException {

        InputStream input = this.getClass().getResourceAsStream("/application.properties");
        Properties prop = new Properties();
        prop.load(input);

        return prop.getProperty("pathUpload");
    }
}