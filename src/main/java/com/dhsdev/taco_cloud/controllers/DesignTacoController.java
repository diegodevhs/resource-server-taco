package com.dhsdev.taco_cloud.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dhsdev.taco_cloud.dtos.taco.TacoCreateRequest;
import com.dhsdev.taco_cloud.mappers.TacoMapper;
import com.dhsdev.taco_cloud.models.Ingredient;
import com.dhsdev.taco_cloud.models.Ingredient.Type;
import com.dhsdev.taco_cloud.repositories.IngredientRepository;
import com.dhsdev.taco_cloud.repositories.OrderRepository;

import jakarta.validation.Valid;

import com.dhsdev.taco_cloud.models.Taco;
import com.dhsdev.taco_cloud.models.TacoOrder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private final OrderRepository orderRepository;

    public DesignTacoController(IngredientRepository ingredientRepository, OrderRepository orderRepository) {
        this.ingredientRepository = ingredientRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_readIngredients')")
    public String showDesignForm(Model model) {
        model.addAttribute("taco", new TacoCreateRequest());
        model.addAttribute("wrap", ingredientRepository.findByType(Type.WRAP));
        model.addAttribute("protein", ingredientRepository.findByType(Type.PROTEIN));
        model.addAttribute("cheese", ingredientRepository.findByType(Type.CHEESE));
        model.addAttribute("veggies", ingredientRepository.findByType(Type.VEGGIES));
        model.addAttribute("sauce", ingredientRepository.findByType(Type.SAUCE));
        return "design";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_writeIngredients')")
    public String processTaco(@Valid TacoCreateRequest tacoDto, Errors errors, @ModelAttribute TacoOrder order) {
        if (errors.hasErrors()) {
            return "design";
        }
        List<Ingredient> ingredients = ingredientRepository.findAllById(tacoDto.getIngredientsIds());
        var taco = TacoMapper.toEntity(tacoDto, ingredients);
        order.addTaco(taco);

        log.info("Processing taco: " + taco);
        return "redirect:/orders/current";
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));

        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    private List<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type) {
        return StreamSupport.stream(ingredients.spliterator(), false)
                .filter(i -> i.getType().equals(type))
                .collect(Collectors.toList());
    }
}