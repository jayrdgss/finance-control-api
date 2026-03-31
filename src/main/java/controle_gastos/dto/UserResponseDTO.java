package controle_gastos.dto;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String nome,
        String email,
        LocalDateTime dataCriacao
) {
}
