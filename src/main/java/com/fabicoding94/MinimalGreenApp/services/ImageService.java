package com.fabicoding94.MinimalGreenApp.services;

import com.fabicoding94.MinimalGreenApp.entities.post.Image;
import com.fabicoding94.MinimalGreenApp.exceptions.NotFoundException;
import com.fabicoding94.MinimalGreenApp.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    ImageRepository repository;

    public Image save(Image image) {
        return repository.save(image);
    }

    public List<Image> getAll() {
        return repository.findAll();
    }

    public Image getById(Long id) {

        Optional<Image> image = repository.findById(id);

        if(image.isEmpty())
            throw new NotFoundException("Image not found!");

        return image.get();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
