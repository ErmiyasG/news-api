package org.afrolink.er.news_api.article.article;

import org.afrolink.er.news_api.common.response.ApiResponse;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import org.afrolink.er.news_api.article.dto.*;
import org.afrolink.er.news_api.article.entity.Article;
import org.afrolink.er.news_api.article.entity.ArticleStatus;
import org.afrolink.er.news_api.article.repository.ArticleRepository;
import org.afrolink.er.news_api.common.response.ApiResponse;
import org.afrolink.er.news_api.user.entity.User;
import org.afrolink.er.news_api.user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    // private final ReadLogService readLogService;

    @Transactional
    public ApiResponse<?> createArticle(
            UUID authorId,
            CreateArticleRequest request) {

        User author = userService.validateAuthor(authorId);

        Article article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .status(
                        request.getStatus() == null
                                ? ArticleStatus.DRAFT
                                : request.getStatus())
                .author(author)
                .build();

        Article savedArticle = articleRepository.save(article);

        return ApiResponse.builder()
                .success(true)
                .message("Article created successfully")
                .object(savedArticle)
                .build();
    }

    @Transactional
    public ApiResponse<?> updateArticle(
            UUID articleId,
            UUID authorId,
            UpdateArticleRequest request) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException(
                        "Article not found"));

        if (!article.getAuthor()
                .getId()
                .equals(authorId)) {

            return ApiResponse.builder()
                    .success(false)
                    .message("Forbidden")
                    .build();
        }

        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setCategory(request.getCategory());

        if (request.getStatus() != null) {
            article.setStatus(request.getStatus());
        }

        articleRepository.save(article);

        return ApiResponse.builder()
                .success(true)
                .message("Article updated successfully")
                .object(article)
                .build();
    }

    @Transactional
    public ApiResponse<?> deleteArticle(
            UUID articleId,
            UUID authorId) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException(
                        "Article not found"));

        if (!article.getAuthor()
                .getId()
                .equals(authorId)) {

            return ApiResponse.builder()
                    .success(false)
                    .message("Forbidden")
                    .build();
        }

        articleRepository.delete(article);

        return ApiResponse.builder()
                .success(true)
                .message("Article deleted successfully")
                .build();
    }

    public ApiResponse<?> getMyArticles(
            UUID authorId,
            int page,
            int size) {

        Page<Article> articles = articleRepository.findByAuthorId(
                authorId,
                PageRequest.of(page, size));

        return ApiResponse.builder()
                .success(true)
                .message("Articles fetched")
                .object(articles)
                .build();
    }

    public ApiResponse<?> getArticle(
            UUID articleId,
            UUID readerId) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException(
                        "Article not found"));

        readLogService.logRead(
                article.getId(),
                readerId);

        return ApiResponse.builder()
                .success(true)
                .message("Article fetched")
                .object(article)
                .build();
    }

    public ApiResponse<?> getPublishedArticles(
            int page,
            int size) {

        Page<Article> articles = articleRepository.findByStatus(
                ArticleStatus.PUBLISHED,
                PageRequest.of(page, size));

        return ApiResponse.builder()
                .success(true)
                .message("Published articles")
                .object(articles)
                .build();
    }
}