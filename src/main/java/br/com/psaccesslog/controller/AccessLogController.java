package com.ps.pslogcrud.controller;

import com.ps.pslogcrud.entities.AccessLog;
import com.ps.pslogcrud.repositories.AccessLogRepository;
import com.ps.pslogcrud.service.AccessLogService;
import com.ps.pslogcrud.service.FileSystemStorageService;
import com.ps.pslogcrud.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AccessLogController {

    @Autowired
    private AccessLogRepository accessLogRepository;
    @Autowired
    private FileSystemStorageService fileSystemStorageService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private AccessLogService accessLogService;

    @GetMapping("/")
    public String index() {
        return "upload.";
    }

    @GetMapping("/accessLogs")
    public ResponseEntity<List<AccessLog>> getAllL() {
        List<AccessLog> accessLogs = accessLogRepository.findAll();
        return new ResponseEntity<>(accessLogs, HttpStatus.OK);
    }

    @PostMapping(value = "/accessLog/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<AccessLog>> uploadLog(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        String fileName;
        String pathStorage;

        List<AccessLog> accessLog;
        fileName = file.getOriginalFilename();
        pathStorage = fileSystemStorageService.getPath();

        storageService.store(file);
        file.getOriginalFilename();

        try {
            accessLog = accessLogService.deserializeLog(pathStorage, fileName);
            accessLogRepository.saveAll(accessLog);
            return new ResponseEntity(accessLog, HttpStatus.OK);
        } catch (Exception e) {
//            return new ResponseEntity<>((List<AccessLog>) new ErrorDetails(new Date(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>((List<AccessLog>) new Date(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @CrossOrigin
//    @GetMapping("/logs/{id}")
//    public ResponseEntity<Log> getById(@PathVariable(value = "id") long logId) {
//        Log log = logRepository.findById(logId).orElseThrow(() -> new ResourceNotFoundException("Log not found for this id: " + logId));
//        return new ResponseEntity<>(log, HttpStatus.OK);
//    }
//
//    @GetMapping("/logs/ip")
//    public ResponseEntity<List<Log>> getByIp(@RequestParam String ip) {
//        List<Log> logs = null;
//        logs = logRepository.finByIp(ip);
//        return new ResponseEntity<>(logs, HttpStatus.OK);
//    }
//
//    @GetMapping("/logs/request")
//    public ResponseEntity<List<Log>> getByRequest(@RequestParam String request) {
//        List<Log> logs = null;
//        logs = logRepository.finByRequest(request);
//        return new ResponseEntity<>(logs, HttpStatus.OK);
//    }
//
//    @PostMapping("/logs")
//    public ResponseEntity<Log> create(@Valid @RequestBody Log log) {
//        logRepository.save(log);
//        return new ResponseEntity<>(log, HttpStatus.OK);
//    }
//
//    @PutMapping("/logs/{id}")
//    public ResponseEntity<Log> update(@PathVariable(value = "id") long logId, @Valid @RequestBody Log log) {
//        Log logPersisted = logRepository.findById(logId).orElseThrow(() -> new ResourceNotFoundException("Log not found fot this id: " + logId));
//
//        logPersisted.setDate(log.getDate());
//        logPersisted.setIp(log.getIp());
//        logPersisted.setRequest(log.getRequest());
//        logPersisted.setStatus(log.getStatus());
//        logPersisted.setUserAgent(log.getUserAgent());
//
//        logRepository.save(logPersisted);
//        return new ResponseEntity<>(log, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/logs/{id}")
//    public ResponseEntity<Log> delete(@PathVariable(value = "id") long logId) {
//        Log log = logRepository.findById(logId).orElseThrow(() -> new ResourceNotFoundException("Log not found for this id: " + logId));
//        logRepository.delete(log);
//        return new ResponseEntity<>(log, HttpStatus.OK);
//    }
//

}
