package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.entity.Picture;
import com.example.demo.repository.PictureRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Picture create(byte[] data, Article article) {
        Picture picture = new Picture(data, article);
        return this.pictureRepository.saveAndFlush(picture);
    }

    @Override
    public List<Picture> getAllByArticle(Article article) {
        return this.pictureRepository.findAllByArticleOrderByIdAsc(article);
    }

    @Override
    public void removePicture(Integer id) {
        this.pictureRepository.delete(this.pictureRepository.findOne(id));
    }
}

