package com.example.demo.repository;


import com.example.demo.entity.Article;
import com.example.demo.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture,Integer> {

    List<Picture> findAllByArticleOrderByIdAsc(Article article);
}
