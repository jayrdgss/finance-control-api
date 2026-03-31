package controle_gastos.dto;

import controle_gastos.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequestDTO(
        @NotBlank(message = "A descricao e obrigatoria.")
        String descricao,
        @NotNull(message = "O valor e obrigatorio.")
        @Positive(message = "O valor deve ser maior que zero.")
        BigDecimal valor,
        @NotNull(message = "O tipo e obrigatorio.")
        TransactionType tipo,
        @NotNull(message = "A data e obrigatoria.")
        LocalDateTime data,
        @NotNull(message = "O categoryId e obrigatorio.")
        Long categoryId
) {
}
