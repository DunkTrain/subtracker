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
@Schema(description = "DTO для обновления пользователя")
public class UserUpdateDto {

    @NotBlank
    @Schema(description = "Новое имя пользователя", example = "Ivanov")
    private String name;

    @NotBlank
    @Email
    @Schema(description = "Новый email пользователя", example = "ivanov@mail.ru")
    private String email;
}
