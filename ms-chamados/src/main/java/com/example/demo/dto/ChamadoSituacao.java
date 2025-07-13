package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;

public record ChamadoSituacao (
        @NotNull(message = "A situação é obrigatória")
        String situacao
)  {

}