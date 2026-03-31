package controle_gastos.controller;

import controle_gastos.dto.CategoryRequestDTO;
import controle_gastos.dto.CategoryResponseDTO;
import controle_gastos.entity.User;
import jakarta.validation.Valid;
import controle_gastos.service.CategoryService;
import controle_gastos.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> criarCategoria(
            @Valid @RequestBody CategoryRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userService.buscarUsuarioPorEmail(userDetails.getUsername());
        CategoryResponseDTO response = categoryService.criarCategoria(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> listarCategorias(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userService.buscarUsuarioPorEmail(userDetails.getUsername());
        List<CategoryResponseDTO> response = categoryService.listarCategorias(user);
        return ResponseEntity.ok(response);
    }
}
