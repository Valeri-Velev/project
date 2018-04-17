package com.example.demo.BindingModels;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class ArticleBindingModel {
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private Integer categoryId;
    @NotNull
    private String tagString;
    @NotNull
    private Double price;
    @NotNull
    private MultipartFile[] pictures;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public MultipartFile[] getPictures() {
        return pictures;
    }

    public void setPictures(MultipartFile[] pictures) {
        this.pictures = pictures;
    }

    public String getTagString() {
        return tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
