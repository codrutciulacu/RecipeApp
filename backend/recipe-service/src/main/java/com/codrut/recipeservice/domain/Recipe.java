package com.codrut.recipeservice.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@RequiredArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final String title;
    private final String description;
    private final String pictureUrl;

    public Recipe() {
        title = null;
        description = null;
        pictureUrl = null;
    }
}
