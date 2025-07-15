package com.dhsdev.taco_cloud.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dhsdev.taco_cloud.models.Ingredient;
import com.dhsdev.taco_cloud.repositories.IngredientRepository;

@RestController
@RequestMapping(path = "/api/ingredients", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_readIngredients')")
    public List<Ingredient> findAll(Authentication authentication){
        System.out.println("Auth principal: " + authentication.getPrincipal());
        return ingredientRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SCOPE_writeIngredients')")
    public Ingredient saveIngredient(@RequestBody Ingredient ingredient){
        return ingredientRepository.save(ingredient);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_deleteIngredients')")
    public void deleteIngredient(@PathVariable String id){
        ingredientRepository.deleteById(id);
    }
}
