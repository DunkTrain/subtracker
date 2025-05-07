package ru.shevchenko.subtracker.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для создания пользователя")
public class UserCreateDto {

    @NotBlank
    @Schema(description = "Имя пользователя", example = "Ivan")
    private String name;

    @NotBlank
    @Email
    @Schema(description = "Email пользователя", example = "ivan@mail.ru")
    private String email;
}
