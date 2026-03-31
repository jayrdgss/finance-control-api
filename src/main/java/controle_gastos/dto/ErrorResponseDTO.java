package controle_gastos.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(
        String error,
        String message,
        LocalDateTime timestamp,
        List<FieldValidationErrorDTO> fields
) {
}
