package com.codrut.recipeservice.controller.dto;

import java.time.LocalDate;

public class ResponseDTO<T> {
    public boolean success = true;
    public T message;
    public LocalDate date = LocalDate.now();

    public ResponseDTO(T message) {
        this.message = message;
    }

    public ResponseDTO(T message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
