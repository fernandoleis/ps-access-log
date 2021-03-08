package com.ps.pslogcrud.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_access_log")
public class AccessLog implements Serializable {

    private static final long serialVersionUID = 8086754301294240296L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "date", nullable = true)
    private Date date;

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "request", nullable = false)
    private String request;

    @Column(name = "status", nullable = false)
    private int status;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Column(name = "userAgent", nullable = false)
    private String userAgent;

    public AccessLog() {
    }

    public AccessLog(Date date, String ip, int status, String userAgent) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
