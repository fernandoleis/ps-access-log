package com.ps.pslogcrud.service;

import com.ps.pslogcrud.entities.AccessLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Service
public class AccessLogService {
    public List<AccessLog> deserializeLog(String filePath, String fileName) throws IOException {
        AccessLog accessLog = null;
        List<AccessLog> logs = new ArrayList();

        try (FileInputStream file = new FileInputStream(filePath + "/" + fileName);
             Scanner fileLog = new Scanner(file);) {
            while (fileLog.hasNextLine()) {
                String line = fileLog.nextLine();
                accessLog = lineToLog(line);
                logs.add(accessLog);
            }

        } catch (FileNotFoundException e) {
//            ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage());
            new ResponseEntity("teste", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return logs;
    }

    public AccessLog lineToLog(String line) {
        AccessLog accessLog = new AccessLog();

        try {
            String[] arrayLog = line.split("\\|");

            Date dateLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(arrayLog[0]);
            accessLog.setDate(dateLog);
            accessLog.setIp(arrayLog[1]);
            accessLog.setRequest(arrayLog[2]);
            accessLog.setStatus(new Integer(arrayLog[3]));
            accessLog.setUserAgent(arrayLog[4]);
        } catch (Exception e) {
//            ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage());
            new ResponseEntity("teste", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return accessLog;
    }
}
