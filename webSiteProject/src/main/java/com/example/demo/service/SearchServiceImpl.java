package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.viewModel.SearchViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    private final UserRepository userRepository;

    @Autowired
    public SearchServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<SearchViewModel> authorArticles(String author) {
        User user = this.userRepository.findByName(author);
        if (!this.userRepository.exists(user.getId())){
            return null;
        }
        List<SearchViewModel> articles = new ArrayList<>();
        for(Article article : user.getArticles()){
            articles.add(new SearchViewModel(article.getTitle()));
        }

        return articles;
    }
}
