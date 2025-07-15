package com.dhsdev.taco_cloud.dtos.taco;

import java.util.List;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TacoCreateRequest {
    @NotBlank(message = "name necessary")
    @Size(min = 5, max = 70, message = "Name must be at least 5 characters long")
    private String name;
    
    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<String> ingredientsIds;
}
