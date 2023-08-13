package com.codrut.recipe


import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class RecipeWriteDto(
    @field:NotNull
    @field:Size(min = 3, max = 32)
    val title: String?,
    @field:NotNull
    @field:Size(min = 3, max = 512)
    val body: String?,
    @field:NotNull
    @field:Size(min = 3, max = 64)
    val createdBy: String?,
) {

    constructor() : this("", "", "")

    fun toEntity(): Recipe = Recipe(
        title = title ?: "",
        body = body ?: "",
        createdBy = createdBy ?: ""
    )
}

@RestController
@RequestMapping("/api/v1/recipe")
class RecipeController(
    private val repo: RecipeRepository
) {

    @PostMapping("/")
    suspend fun createRecipe(@Valid @RequestBody recipeDto: RecipeWriteDto) {
        repo.save(recipeDto.toEntity())
    }

    @GetMapping("/")
    suspend fun getAll() =
        repo.findAll()

    @GetMapping("/{id}")
    suspend fun getById(@PathVariable("id") id: UUID) =
        repo.findById(id)
}