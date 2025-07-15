package com.dhsdev.taco_cloud.controllers;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhsdev.taco_cloud.dtos.taco.TacoCreateRequest;
import com.dhsdev.taco_cloud.dtos.taco.TacoResponse;
import com.dhsdev.taco_cloud.mappers.TacoMapper;
import com.dhsdev.taco_cloud.models.Ingredient;
import com.dhsdev.taco_cloud.models.Taco;
import com.dhsdev.taco_cloud.repositories.IngredientRepository;
import com.dhsdev.taco_cloud.repositories.TacoRepository;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/tacos")
@CrossOrigin(origins = "http://localhost:8080")
public class TacoController {

    private final TacoRepository tacoRepository;
    private final IngredientRepository ingredientRepository;

    public TacoController(TacoRepository tacoRepository, IngredientRepository ingredientRepository) {
        this.tacoRepository = tacoRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping(params = "recent")
    public List<Taco> recentTacos(){
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return tacoRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> findById(@PathVariable Long id){
        var taco = tacoRepository.findById(id);
        if (taco.isPresent()) {
            return ResponseEntity.ok(taco.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping
    public ResponseEntity<TacoResponse> save(@Valid @RequestBody TacoCreateRequest request, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<Ingredient> ingredients = ingredientRepository.findAllById(request.getIngredientsIds());

        Taco newTaco = TacoMapper.toEntity(request, ingredients);

        Taco savedTaco = tacoRepository.save(newTaco);

        TacoResponse response = TacoMapper.toResponse(savedTaco);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
