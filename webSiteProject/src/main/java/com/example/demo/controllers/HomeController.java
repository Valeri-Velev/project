package com.example.demo.controllers;

import com.example.demo.entity.Article;
import com.example.demo.entity.Category;
import com.example.demo.entity.User;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;


@Controller
public class HomeController {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Category> categoryList=this.categoryRepository.findAll();
        List<User> users=this.userRepository.findAll();
        model.addAttribute("users",users);
        model.addAttribute("view", "home/index");
        model.addAttribute("categoryList",categoryList);
        return "base-layout";
    }

    @GetMapping("/category/{id}")
    public String listArticles(Model model, @PathVariable Integer id) {
        if (!this.categoryRepository.exists(id)) {
            return "redirect:/";
        }

        Category category = this.categoryRepository.findOne(id);
        Set<Article> articles = category.getArticles();
        List<User> users=this.userRepository.findAll();
        model.addAttribute("users",users);
        model.addAttribute("articles", articles);
        model.addAttribute("category", category);
        model.addAttribute("view", "home/list-articles");
        return "base-layout";

    }

    @GetMapping("/catalog")
    public String catalog(Model model) {
        Set<Article> articleSet = new HashSet<>();
        List<Category> category = this.categoryRepository.findAll();
        if (articleRepository.findAll() != null) {
            category.addAll(categoryRepository.findAll());
            articleSet.addAll(this.articleRepository.findAll());
        }
        List<User> users=this.userRepository.findAll();
        model.addAttribute("users",users);
        model.addAttribute("category", category);
        model.addAttribute("articleSet", articleSet);
        model.addAttribute("view", "home/catalog");
        return "base-layout";
    }

    @GetMapping("home/articles-by-author/{authorId}")
    public String searchByAuthor(Model model, @PathVariable("authorId") Integer authoId)
    {
        List<User> users=this.userRepository.findAll();
        User user=this.userRepository.findOne(authoId);
        if(user.getArticles().isEmpty()) return "error/zero-posts";
                Set<Article> articles = user.getArticles();
                model.addAttribute("articles", articles);
                model.addAttribute("authorId",authoId);
                model.addAttribute("users",users);
                model.addAttribute("view", "home/articles-by-author");
        return "base-layout";
    }
}
