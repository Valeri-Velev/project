package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaginationService {
    Page<Article> getArticleForPage(Category category, Pageable pageable);
}


