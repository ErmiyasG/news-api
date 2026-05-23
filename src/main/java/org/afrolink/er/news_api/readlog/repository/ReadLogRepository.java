package org.afrolink.er.news_api.readlog.repository;

import org.afrolink.er.news_api.readlog.entity.ReadLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReadLogRepository
        extends JpaRepository<ReadLog, UUID> {
}