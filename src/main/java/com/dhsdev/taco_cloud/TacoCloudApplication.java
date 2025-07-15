package com.dhsdev.taco_cloud;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dhsdev.taco_cloud.models.Ingredient;
import com.dhsdev.taco_cloud.models.Ingredient.Type;
import com.dhsdev.taco_cloud.models.Taco;
import com.dhsdev.taco_cloud.repositories.IngredientRepository;
import com.dhsdev.taco_cloud.repositories.TacoRepository;
import com.dhsdev.taco_cloud.security.user.UserRepository;

@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(
            IngredientRepository repo,
            UserRepository userRepo,
            PasswordEncoder encoder,
            TacoRepository tacoRepo) {
        return args -> {

            repo.deleteAll();

            var flourTortilla = repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
            var cornTortilla = repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
            var groundBeef = repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
            var carnitas = repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
            var tomatoes = repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
            var lettuce = repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
            var cheddar = repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
            var jack = repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
            var salsa = repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
            var sourCream = repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));

            Taco taco1 = new Taco();
            taco1.setName("Carnivore");
            taco1.setIngredients(Arrays.asList(
                    flourTortilla, groundBeef, carnitas,
                    sourCream, salsa, cheddar));
            tacoRepo.save(taco1);

            Taco taco2 = new Taco();
            taco2.setName("Bovine Bounty");
            taco2.setIngredients(Arrays.asList(
                    cornTortilla, groundBeef, cheddar,
                    jack, sourCream));
            tacoRepo.save(taco2);

            Taco taco3 = new Taco();
            taco3.setName("Veg-Out");
            taco3.setIngredients(Arrays.asList(
                    flourTortilla, cornTortilla, tomatoes,
                    lettuce, salsa));
            tacoRepo.save(taco3);
        };
    }

}
