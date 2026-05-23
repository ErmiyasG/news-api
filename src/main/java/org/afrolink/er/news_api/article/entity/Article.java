package org.afrolink.er.news_api.article.entity;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "articles")
@SQLDelete(sql = "UPDATE articles SET deleted_at=now() WHERE id=?")
public class Article {

}
