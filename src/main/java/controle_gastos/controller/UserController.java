package controle_gastos.controller;

import controle_gastos.dto.UserRequestDTO;
import controle_gastos.dto.UserResponseDTO;
import jakarta.validation.Valid;
import controle_gastos.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> cadastrarUsuario(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO response = userService.cadastrarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
