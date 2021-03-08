package br.com.psaccesslog.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class AccessLogStorageService {

    @Value("${storeUpload.path}")
    private String storePathUpload;

    public void storage(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Falha ao armazenar arquivo vazio " + filename);
            }
            if (filename.contains("..")) {
                throw new RuntimeException("Não é possível armazenar o arquivo com caminho relativo fora do diretório atual " + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, Paths.get(storePathUpload).resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Falha ao armazenar arquivo " + filename, e);
        }
    }
}