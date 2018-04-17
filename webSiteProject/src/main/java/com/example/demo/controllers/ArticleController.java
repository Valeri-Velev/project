package com.example.demo.controllers;

import com.example.demo.BindingModels.ArticleBindingModel;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.PictureService;
import com.example.demo.viewModel.ImageViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PictureRepository pictureRepository;

    private final PictureService pictureService;

    @Autowired
    public ArticleController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {
        List<Category> categories = this.categoryRepository.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("view", "article/create");
        return "base-layout";
    }

    @PostMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(ArticleBindingModel articleBindingModel) throws IOException {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User userEntity = this.userRepository.findByEmail(user.getUsername());
        Category category = this.categoryRepository.findOne(articleBindingModel.getCategoryId());
        HashSet<Tag> tags = this.findTagsFromString(articleBindingModel.getTagString());
        Article articleEntity = new Article(articleBindingModel.getTitle(),
                articleBindingModel.getContent(), userEntity,category, tags,articleBindingModel.getPrice());
        this.articleRepository.saveAndFlush(articleEntity);
        for (MultipartFile file : articleBindingModel.getPictures()) {
            this.pictureService.create(file.getBytes(), articleEntity);
        }
        return "redirect:/";
    }

    @GetMapping("/article/{id}")
    public String details(Model model, @PathVariable Integer id) {
        if (!this.articleRepository.exists(id)) {
            return "redirect:/";
        }

        if (!(SecurityContextHolder.getContext().getAuthentication()
                instanceof AnonymousAuthenticationToken)) {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

            User entityUser = this.userRepository.findByEmail(principal.getUsername());

            model.addAttribute("user", entityUser);
        }


        Article article = this.articleRepository.findOne(id);
        List<String> pictures = new ArrayList<>();
        //List<Picture> pictureList = this.pictureService.getAllByArticle(article);
        for (Picture picture : article.getPictures()){
            String encoded = Base64.getEncoder().encodeToString(picture.getData());
            pictures.add(encoded);
        }

        model.addAttribute("pictures",pictures);
        model.addAttribute("article", article);
        model.addAttribute("view", "article/details");
        return "base-layout";
    }

    @GetMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Integer id, Model model) {
        if (!this.articleRepository.exists(id)) {
            return "redirect:/";
        }
        Article article = this.articleRepository.findOne(id);

        if (!isUserAuthorOrAdmin(article))
            return "redirect:/article/" + id;

        List<Category> categories = this.categoryRepository.findAll();

        String tagString = article.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.joining(", "));


        List<ImageViewModel> pictures= new ArrayList<>();
        for(Picture picture : article.getPictures())
        {
            pictures.add(new ImageViewModel(Base64.getEncoder().encodeToString(picture.getData()),
                            picture.getId()));
        }
        model.addAttribute("view", "article/edit");
        model.addAttribute("article", article);
        model.addAttribute("pictures",pictures);
        model.addAttribute("categories", categories);
        model.addAttribute("tags", tagString);

        return "base-layout";
    }

    @PostMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable Integer id,
                              ArticleBindingModel articleBindingModel) throws IOException {
        if (!this.articleRepository.exists(id)) {
            return "redirect:/";
        }

        Article article = this.articleRepository.findOne(id);

        if (!isUserAuthorOrAdmin(article))
            return "redirect:/article/" + id;

        Category category = this.categoryRepository
                .findOne(articleBindingModel.getCategoryId());

        HashSet<Tag> tags = this.findTagsFromString(articleBindingModel.getTagString());
        article.setPrice(articleBindingModel.getPrice());
        article.setContent(articleBindingModel.getContent());
        article.setTitle(articleBindingModel.getTitle());
        article.setCategory(category);
        article.setTags(tags);
        this.articleRepository.saveAndFlush(article);

        for (MultipartFile file : articleBindingModel.getPictures()) {
            this.pictureService.create(file.getBytes(), article);
        }

        return "redirect:/article/" + id;
    }

    @GetMapping("/article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(Model model, @PathVariable Integer id) {
        if (!this.articleRepository.exists(id)) {
            return "redirect:/";
        }

        Article article = this.articleRepository.findOne(id);

        if (!isUserAuthorOrAdmin(article))
            return "redirect:/article/" + id;

        model.addAttribute("article", article);
        model.addAttribute("view", "article/delete");
        return "base-layout";
    }

    @PostMapping("/article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer id) {
        if (!this.articleRepository.exists(id)) {
            return "redirect:/";
        }

        Article article = this.articleRepository.findOne(id);

        if (!isUserAuthorOrAdmin(article))
            return "redirect:/article/" + id;

        this.articleRepository.delete(article);
        return "redirect:/";
    }

    private boolean isUserAuthorOrAdmin(Article article) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());

        return userEntity.isAdmin() || userEntity.isAuthor(article);
    }

    private HashSet<Tag> findTagsFromString(String tagString) {
        HashSet<Tag> tags = new HashSet<>();
        String[] tagNames = tagString.split(",\\s*");

        for (String tagName : tagNames) {
            Tag currentTag = this.tagRepository.findByName(tagName);

            if (currentTag == null) {
                currentTag = new Tag(tagName);
                this.tagRepository.saveAndFlush(currentTag);
            }

            tags.add(currentTag);
        }
        return tags;
    }
}
