package com.example.demo.viewModel;

public class MyArticlesViewModel {
    private String title;
    private String tags;
    private String category;
    private int id;

    public MyArticlesViewModel(String title, String tags, String category,int id) {
        this.title = title;
        this.tags = tags;
        this.category = category;
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
