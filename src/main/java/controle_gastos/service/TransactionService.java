package controle_gastos.service;

import controle_gastos.dto.TransactionRequestDTO;
import controle_gastos.dto.TransactionResponseDTO;
import controle_gastos.entity.Category;
import controle_gastos.entity.Transaction;
import controle_gastos.entity.User;
import controle_gastos.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    public TransactionService(
            TransactionRepository transactionRepository,
            CategoryService categoryService,
            UserService userService
    ) {
        this.transactionRepository = transactionRepository;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public TransactionResponseDTO criarTransacao(TransactionRequestDTO request, String emailUsuarioAutenticado) {
        User user = userService.buscarUsuarioPorEmail(emailUsuarioAutenticado);
        Category category = categoryService.buscarCategoriaDoUsuario(request.categoryId(), user);

        if (request.tipo() != category.getTipo()) {
            throw new RuntimeException("O tipo da transacao deve ser compativel com o tipo da categoria.");
        }

        Transaction transaction = new Transaction();
        transaction.setDescricao(request.descricao());
        transaction.setValor(request.valor());
        transaction.setTipo(request.tipo());
        transaction.setData(request.data());
        transaction.setCategory(category);
        transaction.setUser(user);

        Transaction savedTransaction = transactionRepository.save(transaction);
        return toResponseDTO(savedTransaction);
    }

    public List<TransactionResponseDTO> listarTodasDoUsuarioAutenticado(String emailUsuarioAutenticado) {
        User user = userService.buscarUsuarioPorEmail(emailUsuarioAutenticado);

        return transactionRepository.findByUserId(user.getId())
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<TransactionResponseDTO> listarPorUsuario(Long userId, String emailUsuarioAutenticado) {
        userService.validarAcessoAoUsuario(userId, emailUsuarioAutenticado);

        return transactionRepository.findByUserId(userId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private TransactionResponseDTO toResponseDTO(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getDescricao(),
                transaction.getValor(),
                transaction.getTipo(),
                transaction.getData(),
                transaction.getCategory().getId(),
                transaction.getCategory().getNome(),
                transaction.getUser().getId(),
                transaction.getUser().getNome()
        );
    }
}
