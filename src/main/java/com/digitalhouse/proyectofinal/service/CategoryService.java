package com.digitalhouse.proyectofinal.service;

import com.digitalhouse.proyectofinal.entity.CategoryEntity;
import com.digitalhouse.proyectofinal.entity.UserEntity;
import com.digitalhouse.proyectofinal.repository.CategoryRepository;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryEntity> getAllCategories(){
        return categoryRepository.findAll();
    }

    public CategoryEntity getById(Long id){

        Optional<CategoryEntity> category = categoryRepository.findById(id);

        if (category.isEmpty()){
            throw new RuntimeException("Category not found");
        }

        return category.get();
    }

    public CategoryEntity create(CategoryEntity categoryEntity){
        return categoryRepository.save(categoryEntity);
    }

    public CategoryEntity update(Long id, CategoryEntity categoryEntity){

        Optional<CategoryEntity> category = categoryRepository.findById(id);

        if (category.isEmpty()){
            throw new RuntimeException("Category not found");
        }

        CategoryEntity categoryFound = category.get();
        categoryFound.setName(categoryEntity.getName());
        categoryFound.setDescription(categoryEntity.getDescription());
        categoryFound.setImage(categoryEntity.getImage());

        categoryRepository.save(categoryFound);

        return categoryFound;

    }

    public void deleteById(Long id) {

        Optional<CategoryEntity> user = categoryRepository.findById(id);

        if (user.isEmpty()) {
            throw new RuntimeException("Category not found");
        }

        categoryRepository.deleteById(id);

    }

}
