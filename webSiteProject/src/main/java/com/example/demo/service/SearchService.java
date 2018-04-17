package com.example.demo.service;

import com.example.demo.viewModel.SearchViewModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService
{
    List<SearchViewModel> authorArticles(String author);
}
