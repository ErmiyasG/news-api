package org.afrolink.er.news_api.article.dto;

import org.afrolink.er.news_api.article.entity.ArticleStatus;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateArticleRequest {

    @Size(min = 1, max = 150)
    private String title;

    @Size(min = 50)
    private String content;

    private String category;

    private ArticleStatus status;
}