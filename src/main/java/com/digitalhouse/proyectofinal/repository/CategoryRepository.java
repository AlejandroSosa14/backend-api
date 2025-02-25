package com.digitalhouse.proyectofinal.repository;

import com.digitalhouse.proyectofinal.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
}
