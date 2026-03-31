package controle_gastos.service;

import controle_gastos.dto.CategoryRequestDTO;
import controle_gastos.dto.CategoryResponseDTO;
import controle_gastos.entity.Category;
import controle_gastos.entity.User;
import controle_gastos.exception.ResourceNotFoundException;
import controle_gastos.repository.CategoryRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDTO criarCategoria(CategoryRequestDTO request, User user) {
        Category category = new Category();
        category.setNome(request.nome());
        category.setTipo(request.tipo());
        category.setUser(user);

        Category savedCategory = categoryRepository.save(category);
        return toResponseDTO(savedCategory);
    }

    public List<CategoryResponseDTO> listarCategorias(User user) {
        return categoryRepository.findByUser(user)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Category buscarCategoriaDoUsuario(Long categoryId, User user) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria nao encontrada."));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Voce nao tem permissao para acessar categorias de outro usuario.");
        }

        return category;
    }

    private CategoryResponseDTO toResponseDTO(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getNome(),
                category.getTipo()
        );
    }
}
