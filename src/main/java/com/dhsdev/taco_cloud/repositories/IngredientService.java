package com.dhsdev.taco_cloud.repositories;

import com.dhsdev.taco_cloud.models.Ingredient;

public interface IngredientService {
    
  Iterable<Ingredient> findAll();
  Ingredient addIngredient(Ingredient ingredient);
}
