package br.com.psaccesslog.repositories;

import br.com.psaccesslog.entities.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    @Query("from AccessLog a where a.ip = :ip")
    List<AccessLog> findByIp(@Param("ip") String ip);
}
