package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.entity.Category;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.viewModel.ArticleViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class PaginationServiceImpl implements PaginationService{

    private final ArticleRepository articleRepository;

    @Autowired
    public PaginationServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Page<Article> getArticleForPage(Category category, Pageable pageable) {
        return this.articleRepository.findAllByCategoryOrderByIdDesc(category, pageable);
    }
}
