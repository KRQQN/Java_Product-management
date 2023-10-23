package com.example.jakarta.entities;

import jakarta.validation.constraints.NotEmpty;

public record AddProductInput(
        @NotEmpty(message = "You need to add a name") String name,
        @NotEmpty(message = "You need to add a category") String category) {
}
