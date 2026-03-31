package controle_gastos.dto;

import controle_gastos.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        Long id,
        String descricao,
        BigDecimal valor,
        TransactionType tipo,
        LocalDateTime data,
        Long categoryId,
        String categoryNome,
        Long userId,
        String userNome
) {
}
