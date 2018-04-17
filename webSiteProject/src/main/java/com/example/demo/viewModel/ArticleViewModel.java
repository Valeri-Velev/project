package com.example.demo.viewModel;

import com.example.demo.entity.User;


import java.util.List;

public class ArticleViewModel {
    private Integer articleId;
    private String articleTitle;
    private String articleContent;
    private String authorName;
    private Integer categoryId;
    private String categoryName;

    public ArticleViewModel() {
    }

    public ArticleViewModel(Integer articleId, String articleTitle, String articleContent, String authorName, Integer categoryId, String categoryName) {
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.authorName = authorName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
