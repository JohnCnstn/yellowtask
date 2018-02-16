package com.johncnstn.data.repository;

import com.johncnstn.data.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("imageRepository")
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByUserId(long userId);
}
