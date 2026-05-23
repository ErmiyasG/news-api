package org.afrolink.er.news_api.readlog.service;

import org.afrolink.er.news_api.article.entity.Article;
import org.afrolink.er.news_api.article.repository.ArticleRepository;
import org.afrolink.er.news_api.readlog.entity.ReadLog;
import org.afrolink.er.news_api.readlog.repository.ReadLogRepository;
import org.afrolink.er.news_api.user.entity.User;
import org.afrolink.er.news_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReadLogService {

    private final ReadLogRepository readLogRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Async
    public void logRead(
            UUID articleId,
            UUID readerId) {

        try {

            Article article = articleRepository.findById(articleId)
                    .orElseThrow(() -> new RuntimeException(
                            "Article not found"));

            User reader = null;

            if (readerId != null) {

                reader = userRepository.findById(readerId)
                        .orElse(null);
            }

            ReadLog readLog = ReadLog.builder()
                    .article(article)
                    .reader(reader)
                    .build();

            readLogRepository.save(readLog);

        } catch (Exception ex) {

            log.error(
                    "Failed to save read log for article {}",
                    articleId,
                    ex);
        }
    }
}