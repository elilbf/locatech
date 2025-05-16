package br.com.fiap.locatech.locatech.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AluguelRequestDTO(
        @Schema(description = "O id da pessoa que está alugando o veículo", example = "1")
        @NotNull(message = "O id da pessoa não pode ser nulo.")
        Long pessoaId,
        @NotNull(message = "O id do veículo não pode ser nulo.")
        Long veiculoId,
        LocalDate dataInicio,
        LocalDate dataFim) {
}
