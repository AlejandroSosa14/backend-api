package com.digitalhouse.proyectofinal.service.impl;

import com.digitalhouse.proyectofinal.entity.CategoryEntity;
import com.digitalhouse.proyectofinal.entity.UserEntity;
import com.digitalhouse.proyectofinal.exception.ConflictException;
import com.digitalhouse.proyectofinal.exception.ResourceNotFoundException;
import com.digitalhouse.proyectofinal.repository.CategoryRepository;
import com.digitalhouse.proyectofinal.service.ICategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;

    public List<CategoryEntity> getAllCategories(){
        return categoryRepository.findAll();
    }

    public CategoryEntity getById(Long id){

        Optional<CategoryEntity> category = categoryRepository.findById(id);

        if (category.isEmpty()){
            throw new ResourceNotFoundException("Category with ID " + id + " not found");
        }

        return category.get();
    }

    public CategoryEntity create(CategoryEntity categoryEntity, String dir, MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (file != null && !file.isEmpty()) { // Asegurar que el archivo no está vacío
            String uploadedFileUrl = fileUploadService.uploadFiles(dir, List.of(file));
            System.out.println("URL de la imagen subida: " + uploadedFileUrl); // 🔍 Verificar URL en logs

            if (uploadedFileUrl != null && !uploadedFileUrl.isEmpty()) {
                categoryEntity.setImage(objectMapper.writeValueAsString(uploadedFileUrl));
            }
        }
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        System.out.println("Categoría guardada con imagen: " + savedCategory.getImage()); // 🔍 Verificar que la imagen se guardó
        return savedCategory;
    }
    public CategoryEntity update(Long id, CategoryEntity categoryEntity, String dir, MultipartFile file) throws IOException {
        CategoryEntity categoryFound = getById(id);
        categoryFound.setName(categoryEntity.getName());
        categoryFound.setDescription(categoryEntity.getDescription());
        if (file != null) {
            String uploadedFileUrl = fileUploadService.uploadFiles(dir, List.of(file));
            categoryFound.setImage(uploadedFileUrl);
        }

        return categoryRepository.save(categoryFound);
    }

    public void deleteById(Long id) {

        Optional<CategoryEntity> user = categoryRepository.findById(id);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Category with ID " + id + " not found");
        }

        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Category with ID " + id + " cannot be deleted because it has associated cars.");
        }

    }

}
