package org.afrolink.er.news_api.article.repository;

import java.util.UUID;

import org.afrolink.er.news_api.article.entity.Article;
import org.afrolink.er.news_api.article.entity.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface ArticleRepository
        extends JpaRepository<Article, UUID> {

    Page<Article> findByAuthorId(
            UUID authorId,
            Pageable pageable
    );

    Page<Article> findByStatus(
            ArticleStatus status,
            Pageable pageable
    );
}