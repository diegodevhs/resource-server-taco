package com.dhsdev.taco_cloud.mappers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.dhsdev.taco_cloud.dtos.taco.TacoCreateRequest;
import com.dhsdev.taco_cloud.dtos.taco.TacoResponse;
import com.dhsdev.taco_cloud.models.Ingredient;
import com.dhsdev.taco_cloud.models.Taco;

public class TacoMapper {
    public static Taco toEntity(TacoCreateRequest request, List<Ingredient> ingredients){
        Taco taco = new Taco();
        taco.setName(request.getName());
        taco.setIngredients(ingredients);
        taco.setCreatedAt(new Date());
        taco.setUpdatedAt(new Date());
        return taco;
    }
    public static TacoResponse toResponse(Taco taco) {
        TacoResponse response = new TacoResponse();
        response.setId(taco.getId());
        response.setName(taco.getName());
        response.setCreatedAt(taco.getCreatedAt());

        List<String> ingredientNames = taco.getIngredients()
                                           .stream()
                                           .map(Ingredient::getName)
                                           .collect(Collectors.toList());
        response.setIngredientNames(ingredientNames);

        return response;
    }
}
