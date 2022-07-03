package com.codrut.recipeservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@AllArgsConstructor
@EqualsAndHashCode
public class RecipeDTO {
    public Long id;
    public String title;
    public String description;
    public String pictureUrl;
}
