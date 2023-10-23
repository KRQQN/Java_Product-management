package com.example.jakarta.entities;

import java.util.List;

public record ErrorResponseMessage(List<String> errors) {
}
