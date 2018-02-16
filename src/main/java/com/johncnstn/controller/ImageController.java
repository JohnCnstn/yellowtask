package com.johncnstn.controller;

import com.johncnstn.data.entity.Image;
import com.johncnstn.data.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/images")
    public List<Image> getAllImages() {
        return imageService.findAll();
    }
}
