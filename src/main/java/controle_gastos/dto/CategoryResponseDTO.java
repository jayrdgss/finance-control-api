package controle_gastos.dto;

import controle_gastos.enums.TransactionType;

public record CategoryResponseDTO(
        Long id,
        String nome,
        TransactionType tipo
) {
}
