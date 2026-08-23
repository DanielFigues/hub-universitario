package br.edu.hub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public record RegistrationRequest(
        @NotBlank @Size(min = 3, max = 100) @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O nome deve conter apenas letras e espaços.") String studentName,
        @NotBlank @Email @Size(max = 160) String studentEmail
) {
}
