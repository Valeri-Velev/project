package com.example.demo.viewModel;

public class SearchViewModel
{
    private String title;

    public SearchViewModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
