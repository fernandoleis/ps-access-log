package br.com.psaccesslog.service;

import br.com.psaccesslog.entities.AccessLog;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AccessLogService {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public List<AccessLog> deserializer(String filePath, String fileName) throws IOException {
        AccessLog accessLog;
        List<AccessLog> accessLogList = new ArrayList();
        try (FileInputStream file = new FileInputStream(filePath + "/" + fileName);
             Scanner scanner = new Scanner(file);) {
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                accessLog = parseScanner(nextLine);
                accessLogList.add(accessLog);
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Erro ao fazer a encontrar o arquivo de nome : " + fileName, e.getMessage());
        }
        return accessLogList;
    }

    public AccessLog parseScanner(String line) {
        AccessLog accessLog = new AccessLog();

        try {
            String[] arrayLog = line.split("\\|");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            LocalDateTime dateTime = LocalDateTime.parse(arrayLog[0], formatter);
            accessLog.setDate(dateTime);
            accessLog.setIp(arrayLog[1]);
            accessLog.setRequest(arrayLog[2]);
            accessLog.setStatus(new Integer(arrayLog[3]));
            accessLog.setUserAgent(arrayLog[4]);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao fazer a desserialização do arquivo", e.getMessage());
        }
        return accessLog;
    }

//    public static void main(String[] args) {
//        //Obtém LocalDate de hoje
//        LocalDate hoje = LocalDate.now();
//        System.out.println("LocalDate antes de formatar: " + hoje);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        String hojeFormatado = hoje.format(formatter);
//        System.out.println("LocalDate depois de formatar: " + hojeFormatado);
//        //Obtém LocalDateTime de agora
//        LocalDateTime agora = LocalDateTime.now();
//        System.out.println("LocalDateTime antes de formatar: " + agora);
//        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//        String agoraFormatado = agora.format(formatter);
//        System.out.println("LocalDateTime depois de formatar: " + agoraFormatado);
//    }
}
