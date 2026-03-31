package controle_gastos.dto;

import controle_gastos.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequestDTO(
        @NotBlank(message = "O nome e obrigatorio.")
        String nome,
        @NotNull(message = "O tipo e obrigatorio.")
        TransactionType tipo
) {
}
