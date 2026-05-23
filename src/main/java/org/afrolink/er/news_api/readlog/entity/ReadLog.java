package org.afrolink.er.news_api.readlog.entity;

import org.afrolink.er.news_api.article.entity.Article;
import org.afrolink.er.news_api.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Entity
@Table(name = "read_logs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    private User reader;

    @Column(nullable = false)
    private LocalDateTime readAt;

    @PrePersist
    public void prePersist() {
        this.readAt = LocalDateTime.now(ZoneOffset.UTC);
    }
}