package com.ps.pslogcrud.repositories;

import com.ps.pslogcrud.entities.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
//    @Query("from tb_access_log al where al.ip = :ip")
//    List<AccessLog> finByIp(String ip);
//
//    @Query("from tb_access_log al where al.request like CONCAT('%',:request,'%')")
//    List<AccessLog> finByRequest(String request);
}
