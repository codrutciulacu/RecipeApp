package com.codrut.recipe

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.any
import org.mockito.Mockito.doReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient


@ExtendWith(SpringExtension::class)
@WebFluxTest(RecipeController::class, excludeAutoConfiguration = [DatabaseConfiguration::class])
internal class RecipeControllerTest {

    @MockBean
    private lateinit var repo: RecipeRepository

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun createValidRecipe() = runTest {
        val validDto = RecipeWriteDto("Test", "Test", "user1")

        doReturn(validDto.toEntity()).`when`(repo).save(any())

        webClient
            .post().uri("/api/v1/recipe/")
            .bodyValue(validDto)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Recipe::class.java)
    }

    @ParameterizedTest
    @MethodSource("invaildDtos")
    fun createInvalidRecipe(dto: RecipeWriteDto) = runTest {

        doReturn(dto.toEntity()).`when`(repo).save(any())

        webClient
            .post().uri("/api/v1/recipe/")
            .bodyValue(dto)
            .exchange()
            .expectStatus()
            .isBadRequest()
    }

    @Test
    fun getAllRecipesPaginated() = runTest {
        val validDto = RecipeWriteDto("Test", "Test", "user1")

        doReturn(validDto.toEntity()).`when`(repo).save(any())

        webClient
            .post().uri("/api/v1/recipe/")
            .bodyValue(validDto)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Recipe::class.java)
    }

    companion object {
        @JvmStatic
        fun invaildDtos() = listOf(
            RecipeWriteDto("a".repeat(33), "Test", "user1"),
            RecipeWriteDto("Test", "a".repeat(513), "user1"),
            RecipeWriteDto("Test", "Test", "a".repeat(65)),
            RecipeWriteDto("", "Test", "user1"),
            RecipeWriteDto("Test", "", "user1"),
            RecipeWriteDto("Test", "Test", ""),
        )
    }
}