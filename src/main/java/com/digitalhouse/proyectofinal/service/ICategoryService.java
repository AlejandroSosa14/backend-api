package com.digitalhouse.proyectofinal.service;

import com.digitalhouse.proyectofinal.entity.CategoryEntity;

import java.util.List;

public interface ICategoryService {
    List<CategoryEntity> getAllCategories();
    CategoryEntity getById(Long id);
    CategoryEntity create(CategoryEntity categoryEntity);
    CategoryEntity update(Long id, CategoryEntity categoryEntity);
    void deleteById(Long id);
}
