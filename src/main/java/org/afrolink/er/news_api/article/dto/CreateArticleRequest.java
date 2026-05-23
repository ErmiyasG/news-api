package org.afrolink.er.news_api.article.dto;

import org.afrolink.er.news_api.article.entity.ArticleStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateArticleRequest {

    @NotBlank
    @Size(min = 1, max = 150)
    private String title;

    @NotBlank
    @Size(min = 50)
    private String content;

    @NotBlank
    private String category;

    private ArticleStatus status;
}