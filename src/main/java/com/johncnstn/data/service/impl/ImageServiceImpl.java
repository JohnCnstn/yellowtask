package com.johncnstn.data.service.impl;

import com.johncnstn.data.entity.Image;
import com.johncnstn.data.repository.ImageRepository;
import com.johncnstn.data.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Qualifier("imageRepository")
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Image updloadNewImage(Image image) {
        return imageRepository.save(image);
    }
}
