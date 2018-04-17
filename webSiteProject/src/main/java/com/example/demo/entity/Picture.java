package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name="pictures")
public class Picture
{
private Integer id;
private byte[] data;
private  Article article;

    public Picture(byte[] data, Article article) {
        this.data = data;
        this.article = article;
    }

    public Picture() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data)
    {
        this.data = data;
    }
    @ManyToOne()
    @JoinColumn(name = "articleId")
    public Article getArticle()
    {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

}