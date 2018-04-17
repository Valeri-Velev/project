package com.example.demo.controllers.rest;


import com.example.demo.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestPictureController {

    @Autowired
    private PictureService pictureService;

    @GetMapping("/removeImage/{id}")
    public void removeImage(@PathVariable("id") Integer id){
        this.pictureService.removePicture(id);
    }
}
