package com.codrut.recipe

import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

data class Recipe(
    @Id val id: UUID? = null,
    val title: String,
    val body: String,
    @Column("created_by")
    val createdBy: String,
    @Column("created_by")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

@Repository
interface RecipeRepository : CoroutineCrudRepository<Recipe, UUID>