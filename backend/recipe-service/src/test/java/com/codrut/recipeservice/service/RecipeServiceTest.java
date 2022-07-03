package com.codrut.recipeservice.service;

import com.codrut.recipeservice.controller.dto.CreateRecipeDTO;
import com.codrut.recipeservice.controller.dto.RecipeDTO;
import com.codrut.recipeservice.domain.Recipe;
import com.codrut.recipeservice.exceptions.RecipeNotFoundException;
import com.codrut.recipeservice.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private RecipeService service;

    private final Answer<?> mapElementToRecipeDTO = invocation -> {
        var argument = (Recipe) invocation.getArgument(0);
        return new RecipeDTO(
                argument.getId(),
                argument.getTitle(),
                argument.getDescription(),
                argument.getPictureUrl());
    };

    @Test
    void shouldInsertRecipeIntoDatabase() {
        var recipe = new Recipe("Hello", "Lorem ipsum dolor sit amet", "http://picture.com/1");
        var dto = new CreateRecipeDTO("Hello", "Lorem ipsum dolor sit amet", "http://picture.com/1");

        when(mapper.map(any(), eq(Recipe.class))).thenReturn(recipe);

        var result = service.save(dto);

        verify(repository).save(recipe);
        assertEquals(recipe, result);
    }

    @Test
    void shouldReturnOneRecipeFromDatabase() {
        var recipe = new Recipe("Hello", "Lorem ipsum dolor sit amet", "http://picture.com/1");
        var dto = new RecipeDTO(1L, "Hello", "Lorem ipsum dolor sit amet", "http://picture.com/1");

        when(repository.findById(1L)).thenReturn(Optional.of(recipe));
        when(mapper.map(any(), eq(RecipeDTO.class))).then(mapElementToRecipeDTO);
        when(repository.findById(2L)).thenReturn(Optional.empty());

        var resultWithId1 = service.getById(1L);
        resultWithId1.id = 1L;

        assertEquals(dto, resultWithId1);
        assertThrows(RecipeNotFoundException.class, () -> service.getById(2L));
    }


    @Test
    void shouldReturnAllRecipesFromDatabase() {
        var recipes = List.of(new Recipe("Hello", "Lorem ipsum dolor sit amet", "http://picture.com/1"),
                new Recipe("Hello", "Lorem ipsum dolor sit amet", "http://picture.com/2"),
                new Recipe("Hello", "Lorem ipsum dolor sit amet", "http://picture.com/3"),
                new Recipe("Hello", "Lorem ipsum dolor sit amet", "http://picture.com/4"));

        when(repository.findAll()).thenReturn(recipes);
        when(mapper.map(any(), eq(RecipeDTO.class))).then(mapElementToRecipeDTO);

        var result = service.getAll();

        assertEquals(4, result.size());
        result.forEach(elem -> assertEquals(elem.getClass(), RecipeDTO.class));
    }
}