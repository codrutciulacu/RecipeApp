package com.codrut.recipeservice.service;

import com.codrut.recipeservice.controller.dto.CreateRecipeDTO;
import com.codrut.recipeservice.controller.dto.RecipeDTO;
import com.codrut.recipeservice.domain.Recipe;
import com.codrut.recipeservice.exceptions.RecipeNotFoundException;
import com.codrut.recipeservice.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepository repo;
    private final ModelMapper mapper;

    public Recipe save(CreateRecipeDTO dto) {
        var entity = mapper.map(dto, Recipe.class);

        repo.save(entity);

        return entity;
    }

    public List<RecipeDTO> getAll() {
        return repo.findAll().stream()
                .map(entity -> mapper.map(entity, RecipeDTO.class))
                .collect(Collectors.toList());
    }

    public RecipeDTO getById(Long id) {
        var entity = repo.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("The recipe couldn't be found!"));

        return mapper.map(entity, RecipeDTO.class);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
