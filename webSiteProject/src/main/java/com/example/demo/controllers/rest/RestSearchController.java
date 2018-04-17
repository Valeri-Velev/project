package com.example.demo.controllers.rest;


import com.example.demo.service.SearchService;
import com.example.demo.viewModel.SearchViewModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestSearchController {
    private final SearchService searchService;

    public RestSearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(value = "/index/{author}", method = RequestMethod.GET)
    public List<SearchViewModel> userSearch(@PathVariable("author") String author) {
        return this.searchService.authorArticles(author);
    }
}
