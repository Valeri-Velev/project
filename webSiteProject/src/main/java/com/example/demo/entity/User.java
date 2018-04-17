package com.example.demo.entity;


import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="users")
public class User
{
   private Double height;
    private Double weight;
    private String sex;
    private Integer years;
    private String name;
    private Integer id;
    private String password;
    private String email;
    private Set<Role> roles;
    private Set<Article>articles;
    private byte [] profilePicture;
    public User() {
    }
    @OneToMany(mappedBy = "author")
    public Set<Article> getArticles() {
        return articles;
    }
    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.roles=new HashSet<>();
        this.articles=new HashSet<>();
    }

    @Column(name = "name" ,nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name="email",unique = true,nullable = false)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name ="users_roles")
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public void addRole(Role role){
        this.roles.add(role);
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Transient
    public boolean isAdmin(){
        return this.getRoles().stream()
                .anyMatch(role->role.getName().equals("ROLE_ADMIN"));
    }
    @Transient
    public boolean isAuthor(Article article){
        return Objects.equals(this.getId(),
                article.getAuthor().getId());
    }
    @Column(name="height",nullable = true)
    @Nullable
    public Double getHeight() {
        return height;
    }
    public void setHeight(Double height) {
        this.height = height;
        }
    @Column(name="weight",nullable = true)
    @Nullable
    public Double getWeight() {
        return weight;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Column(name = "sex",nullable = true)
    @Nullable
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name="years",nullable = true)
    @Nullable
    public Integer getYears() {
        return years;
    }
    public void setYears(Integer years) {
        this.years = years;
    }

}