package com.example.demo.dto;

import com.example.demo.model.enums.SituacoesChamado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewChamado (
        @NotNull(message = "A ação é obrigatória")
        @NotBlank(message = "A ação não pode estar vazia")
        String acao,
        @NotNull(message = "O objeto é obrigatório")
        @NotBlank(message = "O objeto não pode estar vazio")
        String objeto,
        @NotNull(message = "O detalhamento é obrigatório")
        String detalhamento,
        @NotNull(message = "O usuário é obrigatório")
        Integer user
)  {

}
