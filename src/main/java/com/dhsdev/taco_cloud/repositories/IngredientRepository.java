package com.dhsdev.taco_cloud.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhsdev.taco_cloud.models.Ingredient;
import com.dhsdev.taco_cloud.models.Ingredient.Type;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {
    List<Ingredient> findByType(Type type);
}
