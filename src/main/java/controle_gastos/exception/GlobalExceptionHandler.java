package controle_gastos.exception;

import controle_gastos.dto.ErrorResponseDTO;
import controle_gastos.dto.FieldValidationErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        List<FieldValidationErrorDTO> fields = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldValidationErrorDTO(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .toList();

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                exception.getClass().getSimpleName(),
                "Erro de validacao nos campos informados.",
                LocalDateTime.now(),
                fields
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(AccessDeniedException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleRuntimeException(RuntimeException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
