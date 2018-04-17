package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.entity.Picture;


import java.util.List;

public interface PictureService {

    Picture create(byte[] data, Article article);
    List<Picture> getAllByArticle(Article article);
    void removePicture(Integer id);
}

