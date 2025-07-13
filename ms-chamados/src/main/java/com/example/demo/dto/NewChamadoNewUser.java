package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewChamadoNewUser (
        @NotNull(message = "O handle do usuário é obrigatório")
        @NotBlank(message = "O handle do usuário não pode estar vazio")
        String userHandle,
        @NotNull(message = "O usuário é obrigatório")
        Integer userId
)  {

}