package org.afrolink.er.news_api.article.article;

import java.util.UUID;

import org.afrolink.er.news_api.article.dto.CreateArticleRequest;
import org.afrolink.er.news_api.article.dto.UpdateArticleRequest;
import org.afrolink.er.news_api.article.entity.Article;
import org.afrolink.er.news_api.article.entity.ArticleStatus;
import org.afrolink.er.news_api.article.repository.ArticleRepository;
import org.afrolink.er.news_api.common.response.ApiResponse;
import org.afrolink.er.news_api.readlog.service.ReadLogService;
import org.afrolink.er.news_api.user.entity.User;
import org.afrolink.er.news_api.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final ReadLogService readLogService;

    @Transactional
    public ApiResponse<?> createArticle(
            UUID authorId,
            CreateArticleRequest request) {

        User author = userService.validateAuthor(authorId);

        Article article = new Article();

        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setCategory(request.getCategory());
        article.setStatus(
                request.getStatus() == null
                        ? ArticleStatus.DRAFT
                        : request.getStatus());
        article.setAuthor(author);

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
                .orElseThrow(() -> new RuntimeException("Article not found"));

        validateOwnership(article, authorId);

        if (request.getTitle() != null) {
            article.setTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            article.setContent(request.getContent());
        }

        if (request.getCategory() != null) {
            article.setCategory(request.getCategory());
        }

        if (request.getStatus() != null) {
            article.setStatus(request.getStatus());
        }

        Article updatedArticle = articleRepository.save(article);

        return ApiResponse.builder()
                .success(true)
                .message("Article updated successfully")
                .object(updatedArticle)
                .build();
    }

    @Transactional
    public ApiResponse<?> deleteArticle(
            UUID articleId,
            UUID authorId) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        validateOwnership(article, authorId);

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
                .message("Articles fetched successfully")
                .object(articles)
                .build();
    }

    public ApiResponse<?> getArticle(
            UUID articleId,
            UUID readerId) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        readLogService.logRead(
                article.getId(),
                readerId);

        return ApiResponse.builder()
                .success(true)
                .message("Article fetched successfully")
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
                .message("Published articles fetched successfully")
                .object(articles)
                .build();
    }

    private void validateOwnership(
            Article article,
            UUID authorId) {

        if (!article.getAuthor()
                .getId()
                .equals(authorId)) {

            throw new RuntimeException(
                    "You are not allowed to modify this article");
        }
    }
}