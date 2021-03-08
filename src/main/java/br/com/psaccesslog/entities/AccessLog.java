package br.com.psaccesslog.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_ACCESS_LOG")
public class AccessLog implements Serializable {

    private static final long serialVersionUID = 8086754301294240296L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "DATE", nullable = true)
    private LocalDateTime date;

    @Column(name = "IP", nullable = false)
    private String ip;

    @Column(name = "REQUEST", nullable = false)
    private String request;

    @Column(name = "STATUS", nullable = false)
    private int status;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Column(name = "USERAGENT", nullable = false)
    private String userAgent;

    public AccessLog() {
    }

    public AccessLog(LocalDateTime date, String ip, int status, String userAgent) {
        this.date = date;
        this.ip = ip;
        this.status = status;
        this.userAgent = userAgent;
    }


    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
