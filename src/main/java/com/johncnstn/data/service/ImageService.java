package com.johncnstn.data.service;

import com.johncnstn.data.entity.Image;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageService {
    List<Image> findAll();
    Image updloadNewImage(Image image);
}
