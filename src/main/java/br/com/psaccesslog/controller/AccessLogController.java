package br.com.psaccesslog.controller;

import br.com.psaccesslog.entities.AccessLog;
import br.com.psaccesslog.repositories.AccessLogRepository;
import br.com.psaccesslog.service.AccessLogService;
import br.com.psaccesslog.service.AccessLogStorageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class AccessLogController {

    @Autowired
    private AccessLogRepository accessLogRepository;
    @Autowired
    private AccessLogStorageService accessLogStorageService;
    @Autowired
    private AccessLogService accessLogService;
    @Value("${storeUpload.path}")
    private String storeUploadPath;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @ApiOperation(value = "Redirecionamento para tela de início.")
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @ApiOperation(value = "Lista todos os AccessLogs ou filtra por IP.")
    @GetMapping("/accessLogs")
    public String listar(@RequestParam(required = false) String ip, Model model) {
        if (ip == null) {
            List<AccessLog> accessLogs = accessLogRepository.findAll();
            model.addAttribute("accessLogs", accessLogs);
        } else {
            List<AccessLog> accessLogs = accessLogRepository.findByIp(ip);
            model.addAttribute("accessLogs", accessLogs);
        }
        return "lista";
    }

    @ApiOperation(value = "Pesquisa Access Log por ID.")
    @GetMapping("/accessLog/{id}")
    public ResponseEntity<AccessLog> getById(@PathVariable(value = "id") long id) {
        Optional<AccessLog> accessLog = accessLogRepository.findById(id);
        if (accessLog.isPresent()) {
            return ResponseEntity.ok(accessLog.get());
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Redirecionamento para tela de upload.")
    @GetMapping("/accessLog/upload")
    public String upload() {
        return "upload";
    }

    @ApiOperation(value = "Redireciona para a tela de status de inserção de arquivo de log.")
    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

    @ApiOperation(value = "Upload via Navegador")
    @PostMapping("/accessLog/upload")
    public String uploadBrowser(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Selecione um arquivo");
            return "redirect:uploadStatus";
        }

        List<AccessLog> accessLog;
        String fileName = file.getOriginalFilename();
        try {
            accessLogStorageService.storage(file);
            accessLog = accessLogService.deserializer(storeUploadPath, fileName);
            accessLogRepository.saveAll(accessLog);
            redirectAttributes.addFlashAttribute("message", "Arquivo '" + file.getOriginalFilename() + "' enviado com sucesso");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao fazer upload de arquivo : " + file.getOriginalFilename(), e);
        }
        return "redirect:/uploadStatus";
    }

    @ApiOperation(value = "Api de Upload para teste via POSTMAN.")
    @PostMapping(value = "/accessLog/postman/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AccessLog> uploadPostman(@RequestParam("file") MultipartFile file) {
        List<AccessLog> accessLog;
        String fileName = file.getOriginalFilename();
        try {
            accessLogStorageService.storage(file);
            accessLog = accessLogService.deserializer(storeUploadPath, fileName);
            accessLogRepository.saveAll(accessLog);
            return new ResponseEntity(accessLog, HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao fazer upload de arquivo : " + file.getOriginalFilename(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/accessLog/save", consumes = "application/x-www-form-urlencoded", produces = "application/json")
    public ResponseEntity<Object> salvar(AccessLog accessLog, UriComponentsBuilder uriComponentsBuilder) throws URISyntaxException {
        if (accessLog.getDate() == null) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            LocalDateTime dateTime = LocalDateTime.parse(now.toString().substring(0, 23).replace("T", " "), formatter);
            accessLog.setDate(dateTime);
        }
        accessLogRepository.save(accessLog);
        URI uri = uriComponentsBuilder.path("/{id}").buildAndExpand(accessLog.getId()).toUri();
        return ResponseEntity.created(uri).body(accessLog);
    }

    @PostMapping(path = "/accessLog/postman/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> salvarPostman(@RequestBody AccessLog accessLog, UriComponentsBuilder uriComponentsBuilder) {
        accessLogRepository.save(accessLog);
        URI uri = uriComponentsBuilder.path("/{id}").buildAndExpand(accessLog.getId()).toUri();
        return ResponseEntity.created(uri).body(accessLog);
    }

    @GetMapping("/accessLog/delete")
    public ResponseEntity delete(@RequestParam Long id) {
        Optional<AccessLog> accessLogOptional = accessLogRepository.findById(id);
        if (accessLogOptional.isPresent()) {
            accessLogRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/accessLog/{id}")
    public ResponseEntity remover(@PathVariable long id) {
        Optional<AccessLog> accessLogOptional = accessLogRepository.findById(id);
        if (accessLogOptional.isPresent()) {
            accessLogRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/accessLog/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AccessLog> update(@PathVariable(value = "id") long id, @RequestBody AccessLog log) {
        return accessLogRepository.findById(id)
                .map(accessLog -> {
                    accessLog.setDate(log.getDate());
                    accessLog.setIp(log.getIp());
                    accessLog.setRequest(log.getRequest());
                    accessLog.setStatus(log.getStatus());
                    accessLog.setUserAgent(log.getUserAgent());
                    AccessLog updated = accessLogRepository.save(accessLog);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
}
