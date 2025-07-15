package com.dhsdev.taco_cloud.dtos.taco;

import java.util.Date;
import java.util.List;


import lombok.Data;

@Data
public class TacoResponse {
    private Long id;
    private String name;
    private List<String> ingredientNames;
    private Date createdAt;
}
