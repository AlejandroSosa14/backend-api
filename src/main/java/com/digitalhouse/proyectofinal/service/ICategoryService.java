package com.digitalhouse.proyectofinal.service;

import com.digitalhouse.proyectofinal.entity.CategoryEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ICategoryService {
    List<CategoryEntity> getAllCategories();
    CategoryEntity getById(Long id);
    void deleteById(Long id);

    CategoryEntity create(CategoryEntity categoryEntity, String pathUpload, MultipartFile file) throws IOException;

    CategoryEntity update(Long id, CategoryEntity categoryEntity, String pathUpload, MultipartFile file) throws IOException;
}
