package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")
public class Article {
    private Integer id;
    private String title;
    private String content;
    @JsonBackReference
    private User author;
    @JsonBackReference
    private Category category;
    @JsonBackReference
    private Set<Picture> pictures;
    @JsonBackReference
    private Set<Tag> tags;
    private Double price;

    public Article(String title, String content, User author, Category category, HashSet<Tag> tags,Double price){
        this.title=title;
        this.content=content;
        this.author=author;
        this.category=category;
        this.tags=tags;
        this.price=price;
    }

    public Article() { }

    @ManyToMany()
    @JoinColumn(table="article_tags")
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @ManyToOne()
    @JoinColumn(nullable = false, name = "categoryId")
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(columnDefinition = "text", nullable = false)
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    @Column
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    @ManyToOne()
    @JoinColumn(nullable =false, name = "authorId")
    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }

    @Transient //this method shouldn't be saved in our database
    public String getSummary(){
        return this.getContent().substring(0, this.getContent().length()/2)+"...";
    }

    @OneToMany(mappedBy = "article", orphanRemoval = true)
    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }
}
