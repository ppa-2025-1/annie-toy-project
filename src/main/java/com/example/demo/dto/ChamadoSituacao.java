package com.example.demo.dto;

import com.example.demo.model.enums.SituacoesChamado;

import jakarta.validation.constraints.NotNull;

public record ChamadoSituacao (
        @NotNull(message = "A situação é obrigatória")
        SituacoesChamado situacao
)  {

}
