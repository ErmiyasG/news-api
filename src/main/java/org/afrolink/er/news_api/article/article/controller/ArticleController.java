package org.afrolink.er.news_api.article.article.controller;

import lombok.RequiredArgsConstructor;

import org.afrolink.er.news_api.article.article.ArticleService;
import org.afrolink.er.news_api.article.dto.CreateArticleRequest;
import org.afrolink.er.news_api.article.dto.UpdateArticleRequest;
import org.afrolink.er.news_api.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /**
     * POST /articles
     * Author creates article
     */
    @PostMapping
    @PreAuthorize("hasAuthority('AUTHOR')")
    public ResponseEntity<ApiResponse<?>> createArticle(
            @Valid @RequestBody CreateArticleRequest request,
            Authentication authentication) {

        UUID authorId = UUID.fromString(
                authentication.getName());

        ApiResponse<?> response = articleService.createArticle(
                authorId,
                request);

        return ResponseEntity.ok(response);
    }

    /**
     * GET /articles/me
     * List author's own articles
     */
    @GetMapping("/me")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public ResponseEntity<ApiResponse<?>> getMyArticles(
            Authentication authentication,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size) {

        UUID authorId = UUID.fromString(
                authentication.getName());

        ApiResponse<?> response = articleService.getMyArticles(
                authorId,
                page,
                size);

        return ResponseEntity.ok(response);
    }

    /**
     * PUT /articles/{id}
     * Update article
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public ResponseEntity<ApiResponse<?>> updateArticle(
            @PathVariable UUID id,

            @Valid @RequestBody UpdateArticleRequest request,

            Authentication authentication) {

        UUID authorId = UUID.fromString(
                authentication.getName());

        ApiResponse<?> response = articleService.updateArticle(
                id,
                authorId,
                request);

        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /articles/{id}
     * Soft delete article
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public ResponseEntity<ApiResponse<?>> deleteArticle(
            @PathVariable UUID id,
            Authentication authentication) {

        UUID authorId = UUID.fromString(
                authentication.getName());

        ApiResponse<?> response = articleService.deleteArticle(
                id,
                authorId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getArticle(
            @PathVariable UUID id,
            Authentication authentication) {

        UUID readerId = null;

        if (authentication != null) {

            try {
                readerId = UUID.fromString(
                        authentication.getName());
            } catch (Exception ignored) {
            }
        }

        return ResponseEntity.ok(
                articleService.getArticle(
                        id,
                        readerId));
    }
}