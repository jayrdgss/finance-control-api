package controle_gastos.controller;

import controle_gastos.dto.TransactionRequestDTO;
import controle_gastos.dto.TransactionResponseDTO;
import jakarta.validation.Valid;
import controle_gastos.service.TransactionService;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> criarTransacao(
            @Valid @RequestBody TransactionRequestDTO request,
            Authentication authentication
    ) {
        TransactionResponseDTO response = transactionService.criarTransacao(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> listarTodas(Authentication authentication) {
        List<TransactionResponseDTO> response = transactionService
                .listarTodasDoUsuarioAutenticado(authentication.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionResponseDTO>> listarPorUsuario(
            @PathVariable Long userId,
            Authentication authentication
    ) {
        List<TransactionResponseDTO> response = transactionService
                .listarPorUsuario(userId, authentication.getName());
        return ResponseEntity.ok(response);
    }
}
