package com.codrut.recipeservice.controller.dto;

import lombok.AllArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
public class CreateRecipeDTO {
    private static final String URL_REGEX = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";

    @Size(min = 3, message = "Title is too short!")
    @Size(max = 32, message = "Title is too long!")
    public String title;

    @Size(min = 3, message = "Description is too short!")
    @Size(max = 256, message = "Description is too long!")
    public String description;

    @NotBlank(message = "No picture is given!")
    @Pattern(regexp = URL_REGEX, message = "The image must be an url!")
    public String pictureUrl;
}
