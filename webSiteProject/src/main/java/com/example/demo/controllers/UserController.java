package com.example.demo.controllers;


import com.example.demo.BindingModels.UserBindingModel;
import com.example.demo.entity.Article;
import com.example.demo.entity.Role;
import com.example.demo.entity.Tag;
import com.example.demo.entity.User;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.viewModel.MyArticlesViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class UserController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/register")
    public String register(Model model) {
        List<User> users=this.userRepository.findAll();
        model.addAttribute("users",users);
        model.addAttribute("view", "user/register");
        return "base-layout";
    }

    @GetMapping("/login")
    public String login(Model model) {
        List<User> users=this.userRepository.findAll();
        model.addAttribute("users",users);
        model.addAttribute("view","user/login");
        return "base-layout";

    }

    @PostMapping("/register")
    public String registerProcess(UserBindingModel userBindingModel) throws Exception {
        if (!(userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword()))) {
            return "redirect:/register";
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User(userBindingModel.getFullName(),
                bCryptPasswordEncoder.encode(userBindingModel.getPassword()),
                userBindingModel.getEmail());
        Role userRole=this.roleRepository.findByName("ROLE_USER");
        user.addRole(userRole);
        byte[] profilePicture=userBindingModel.getProfilePicture().getBytes();
        user.setProfilePicture(profilePicture);
        this.userRepository.saveAndFlush(user);
        return "redirect:/login ";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletResponse response, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/login?logout";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model) throws IOException {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(principal.getUsername());
        if((user.getProfilePicture()!=null))
        {
            String profilePicture=Base64.getEncoder().encodeToString(user.getProfilePicture());
            model.addAttribute("profilePicture",profilePicture);
        }
        List<User> users=this.userRepository.findAll();
        model.addAttribute("users",users);
        model.addAttribute("user", user);
        model.addAttribute("view", "user/profile");
        return "base-layout";
    }

    @GetMapping("/profile/properties")
    @PreAuthorize("isAuthenticated()")
    public String properties(Model model){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(principal.getUsername());
        List<User> users=this.userRepository.findAll();
        model.addAttribute("users",users);
        model.addAttribute("user", user);
        model.addAttribute("view", "user/properties");
        return "base-layout";
    }

    @PostMapping("/profile/properties")
    public String propertiesProcess(UserBindingModel userBingingModel){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(principal.getUsername());
        user.setWeight(userBingingModel.getWeight());
        user.setHeight(userBingingModel.getHeight());
        user.setYears(userBingingModel.getYears());
        user.setSex(userBingingModel.getSex());
        this.userRepository.saveAndFlush(user);
        return "redirect:/profile";
    }

    @GetMapping("/profile/myarticles")
    @PreAuthorize("isAuthenticated()")
    public String myArticles(Model model) {

        int id;
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(principal.getUsername());
        List<MyArticlesViewModel> myArticlesViewModelList=new ArrayList<>();
        Set<Article> articleList = user.getArticles();
        String tags="";
        for(Article set : articleList)
        {
            for(Tag tag : set.getTags())
            {
                tags+=tag.getName()+",";
            }
            myArticlesViewModelList.add(new MyArticlesViewModel(set.getTitle(),tags,set.getCategory().getName(),set.getId()));
            tags="";
        }
        List<User> users=this.userRepository.findAll();

        model.addAttribute("articleList",articleList);
        model.addAttribute("users",users);
        model.addAttribute("articles",myArticlesViewModelList);
        model.addAttribute("user", user);
        model.addAttribute("view", "user/myarticles");
        return "base-layout";
    }
    @GetMapping("/profile/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Integer id,Model model)
    {
        if(!this.userRepository.exists(id)) return "redirect:/profile";
        User user= this.userRepository.findOne(id);
        if((user.getProfilePicture()!=null)) {
            String  editedProfilePicture = Base64.getEncoder().encodeToString(user.getProfilePicture());
            model.addAttribute(" editedProfilePicture", editedProfilePicture);
        }
        model.addAttribute("user", user);
        model.addAttribute("view","user/edit");
        return "base-layout";
    }

    @PostMapping("/profile/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable("id") Integer id,UserBindingModel userBindingModel) throws IOException {
        if(!this.userRepository.exists(id)) {
            return "redirect:/profile";
        }
        User user =this.userRepository.findOne(id);
        if(userBindingModel.getProfilePicture() !=null) {
            byte[] editedProfilePicture = userBindingModel.getProfilePicture().getBytes();
            user.setProfilePicture(editedProfilePicture);
        }
        this.userRepository.saveAndFlush(user);
        return "redirect:/profile";
    }

}