package com.digitalhouse.proyectofinal.specification;

import com.digitalhouse.proyectofinal.entity.CarEntity;
import com.digitalhouse.proyectofinal.entity.CategoryEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CarSpecification {
    public static Specification<CarEntity> search(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String[] words = search.trim().split("\\s+");
            List<Predicate> predicates = new ArrayList<>();

            // Hacer JOIN con CategoryEntity
            Join<CarEntity, CategoryEntity> categoryJoin = root.join("category", JoinType.LEFT);

            for (String word : words) {
                String pattern = "%" + word.toLowerCase() + "%";

                Predicate stringPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("transmissionType")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(categoryJoin.get("name")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(categoryJoin.get("description")), pattern)
                );

                try {
                    Integer intValue = Integer.parseInt(word);

                    Predicate numericPredicate = criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("model"), intValue)
                    );

                    predicates.add(numericPredicate);
                } catch (NumberFormatException e1) {
                    try {
                        BigDecimal numericValue = new BigDecimal(word);
                        Predicate numericPredicate = criteriaBuilder.or(
                                criteriaBuilder.equal(root.get("reserveCost"), numericValue)
                        );
                        predicates.add(numericPredicate);
                    } catch (NumberFormatException e2) {
                        predicates.add(stringPredicate);
                    }
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
