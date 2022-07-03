package com.codrut.recipeservice.controller;

import com.codrut.recipeservice.controller.dto.CreateRecipeDTO;
import com.codrut.recipeservice.controller.dto.RecipeDTO;
import com.codrut.recipeservice.controller.dto.ResponseDTO;
import com.codrut.recipeservice.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService service;

    @PostMapping("/")
    public ResponseEntity<ResponseDTO<String>> save(@Valid @RequestBody CreateRecipeDTO dto) {
        service.save(dto);

        return ResponseEntity.ok(new ResponseDTO<>("The recipe was created!"));
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<RecipeDTO>>> getAll() {
        return ResponseEntity.ok(new ResponseDTO<>(service.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<RecipeDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO<>(service.getById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.ok(new ResponseDTO<>("The recipe was deleted!"));
    }

}
