package com.example.demo.controllers.rest;

import com.example.demo.entity.Article;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestPaginationController {
    private final PaginationService paginationService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public RestPaginationController(PaginationService paginationService,
                                    CategoryRepository categoryRepository) {
        this.paginationService = paginationService;
        this.categoryRepository = categoryRepository;
    }

    //Send Article Information
    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public Page<Article> firstAjax(@RequestParam("page") Integer page,
                                   @RequestParam("size") Integer size) {

        PageRequest request = new PageRequest(page, size);
        Category category = this.categoryRepository.findOne(1);
        return this.paginationService.getArticleForPage(category, request);
    }
}

