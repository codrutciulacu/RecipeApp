package com.codrut.recipeservice.controller;

import com.codrut.recipeservice.controller.dto.CreateRecipeDTO;
import com.codrut.recipeservice.controller.dto.RecipeDTO;
import com.codrut.recipeservice.exceptions.RecipeNotFoundException;
import com.codrut.recipeservice.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecipeService recipeService;

    @Test
    void shouldReturnASuccessfulResponse() throws Exception {
        var body = new CreateRecipeDTO("Hello", "Lorem ipsum dolor sit amet", "http://picture.com/1");
        var date = LocalDate.now();

        mockMvc.perform(post("/recipe/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(content().json(" {\"success\":true,\"message\":\"The recipe was created!\",\"date\":\"" + date +"\"}"));

        verify(recipeService).save(any());
    }

    @Test
    void shouldReturnTitleErrorResponse() throws Exception {
        var bodyTooShortTitle = new CreateRecipeDTO("Ab", "Lorem ipsum dolor sit amet", "http://picture.com/1");
        var bodyTooLongTitle = new CreateRecipeDTO("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "Lorem ipsum dolor sit amet", "http://picture.com/1");
        var date = LocalDate.now();

        mockMvc.perform(post("/recipe/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bodyTooShortTitle)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(" {\"success\":false,\"message\":\"Title is too short!\",\"date\":\"" + date +"\"}"));

        mockMvc.perform(post("/recipe/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bodyTooLongTitle)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(" {\"success\":false,\"message\":\"Title is too long!\",\"date\":\"" + date +"\"}"));
    }

    @Test
    void shouldReturnDescriptionErrorResponse() throws Exception {
        var bodyTooShortDescription = new CreateRecipeDTO("Abasda", "Lo", "http://picture.com/1");
        var bodyTooLongDescription = new CreateRecipeDTO("AAAAAA", "\n" +
                "\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean turpis lectus, vestibulum ac consectetur sit amet, bibendum vitae mauris. Aliquam sit amet risus vel turpis laoreet venenatis vel ut nulla. Nullam tempus non lorem nec tempus. Mauris non lectus augue. In molestie nec odio sit amet aliquet. Maecenas vel varius mi, vel dignissim urna. Nunc ac felis a velit consectetur efficitur. Donec vestibulum in orci vel bibendum. Duis molestie mi vestibulum dolor mattis, sit amet mattis mauris vestibulum. Proin sed dui ac enim condimentum interdum ut et risus. Aenean at nunc id metus tristique tristique vitae in metus.\n" +
                "\n" +
                "Nunc sem eros, interdum non placerat dictum, cursus sit amet risus. Sed rutrum rutrum est a ultrices. Nulla facilisi. Duis pretium nisl nunc, ac blandit leo porttitor et. Ut sem diam, blandit at sagittis eget, vestibulum ut nunc. Proin ac ex vitae sapien egestas faucibus. Curabitur molestie arcu leo, fermentum condimentum sem imperdiet non. Aliquam et risus nulla. Pellentesque dapibus turpis in lectus elementum maximus. Suspendisse leo mauris, tincidunt quis tincidunt in, iaculis at orci. Etiam sagittis neque at urna gravida, nec dignissim elit interdum.\n" +
                "\n" +
                "Sed ut arcu nec dui laoreet consectetur. Donec dignissim dui efficitur erat vehicula fringilla. Nunc sit amet ornare augue. Ut ac felis elit. Sed quis enim varius quam varius interdum vel quis neque. Vestibulum in neque massa. In ipsum sem, vehicula sit amet pretium ac, efficitur quis erat. Phasellus ut pellentesque ex. Nullam maximus tellus eu risus rhoncus semper. Aenean semper vehicula quam, in tristique velit lobortis at. Cras ultrices tellus urna, vel vestibulum sem placerat ut. ", "http://picture.com/1");
        var date = LocalDate.now();

        mockMvc.perform(post("/recipe/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bodyTooShortDescription)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(" {\"success\":false,\"message\":\"Description is too short!\",\"date\":\"" + date +"\"}"));

        mockMvc.perform(post("/recipe/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bodyTooLongDescription)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(" {\"success\":false,\"message\":\"Description is too long!\",\"date\":\"" + date +"\"}"));
    }

    @Test
    void shouldReturnPictureUrlErrorResponse() throws Exception {
        var bodyBlankPictureUrl = new CreateRecipeDTO("AAAAAA", "Lorem", "");
        var bodyNotUrl = new CreateRecipeDTO("AAAAAA", "Lorem", "picture+1");
        var date = LocalDate.now();

        mockMvc.perform(post("/recipe/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bodyBlankPictureUrl)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(" {\"success\":false,\"message\":\"No picture is given!\",\"date\":\"" + date +"\"}"));

        mockMvc.perform(post("/recipe/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bodyNotUrl)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(" {\"success\":false,\"message\":\"The image must be an url!\",\"date\":\"" + date +"\"}"));
    }

    @Test
    void shouldReturnAListOfRecipesAsResponse() throws Exception {
        var body = List.of(new RecipeDTO(1L, "Hello1", "Lorem ipsum dolor sit amet", "http://picture.com/1"),
                new RecipeDTO(2L,"Hello2", "Lorem ipsum dolor sit amet", "http://picture.com/2"),
                new RecipeDTO(3L,"Hello3", "Lorem ipsum dolor sit amet", "http://picture.com/3"),
                new RecipeDTO(4L,"Hello4", "Lorem ipsum dolor sit amet", "http://picture.com/4"),
                new RecipeDTO(5L,"Hello5", "Lorem ipsum dolor sit amet", "http://picture.com/5"));
        var date = LocalDate.now();

        when(recipeService.getAll()).thenReturn(body);

        mockMvc.perform(get("/recipe/")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("{\"success\":true,\"message\":[{\"id\":1,\"title\":\"Hello1\",\"description\":\"Lorem ipsum dolor sit amet\",\"pictureUrl\":\"http://picture.com/1\"},{\"id\":2,\"title\":\"Hello2\",\"description\":\"Lorem ipsum dolor sit amet\",\"pictureUrl\":\"http://picture.com/2\"},{\"id\":3,\"title\":\"Hello3\",\"description\":\"Lorem ipsum dolor sit amet\",\"pictureUrl\":\"http://picture.com/3\"},{\"id\":4,\"title\":\"Hello4\",\"description\":\"Lorem ipsum dolor sit amet\",\"pictureUrl\":\"http://picture.com/4\"},{\"id\":5,\"title\":\"Hello5\",\"description\":\"Lorem ipsum dolor sit amet\",\"pictureUrl\":\"http://picture.com/5\"}],\"date\":\""+ date +"\"}"));
    }

    @Test
    void shouldReturnTheRecipeWithTheGivenIdAsResponse() throws Exception {
        var body = new RecipeDTO(1L, "Hello1", "Lorem ipsum dolor sit amet", "http://picture.com/1");
        var date = LocalDate.now();

        when(recipeService.getById(any())).thenReturn(body);

        mockMvc.perform(get("/recipe/{id}", 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("{\"success\":true,\"message\":{\"id\":1,\"title\":\"Hello1\",\"description\":\"Lorem ipsum dolor sit amet\",\"pictureUrl\":\"http://picture.com/1\"}},\"date\":\""+ date +"\"}"));
    }

    @Test
    void shouldReturnAnErrorMessageBecauseCantFindTheRecipeAsResponse() throws Exception {
        var body = new RecipeDTO(1L, "Hello1", "Lorem ipsum dolor sit amet", "http://picture.com/1");
        var date = LocalDate.now();

        when(recipeService.getById(any()))
                .thenThrow(new RecipeNotFoundException("The recipe couldn't be found!"));

        mockMvc.perform(get("/recipe/{id}", 1L)
                        .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(content().json("{\"success\":false,\"message\":\"The recipe couldn't be found!\",\"date\":\""+ date +"\"}"));
    }
}