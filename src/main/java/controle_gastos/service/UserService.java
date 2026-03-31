package controle_gastos.service;

import controle_gastos.dto.LoginRequestDTO;
import controle_gastos.dto.LoginResponseDTO;
import controle_gastos.dto.UserRequestDTO;
import controle_gastos.dto.UserResponseDTO;
import controle_gastos.entity.User;
import controle_gastos.exception.DuplicateResourceException;
import controle_gastos.exception.ResourceNotFoundException;
import controle_gastos.repository.UserRepository;
import controle_gastos.security.JwtService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponseDTO cadastrarUsuario(UserRequestDTO request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email ja cadastrado.");
        }

        User user = new User();
        user.setNome(request.nome());
        user.setEmail(request.email());
        user.setSenha(passwordEncoder.encode(request.senha()));
        user.setDataCriacao(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return toResponseDTO(savedUser);
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Email ou senha invalidos."));

        if (!passwordEncoder.matches(request.senha(), user.getSenha())) {
            throw new RuntimeException("Email ou senha invalidos.");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new LoginResponseDTO("Login realizado com sucesso.", token);
    }

    public User buscarUsuarioPorEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario autenticado nao encontrado."));
    }

    public User buscarUsuarioPorId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado."));
    }

    public User validarAcessoAoUsuario(Long userId, String emailUsuarioAutenticado) {
        User user = buscarUsuarioPorId(userId);

        if (!user.getEmail().equals(emailUsuarioAutenticado)) {
            throw new AccessDeniedException("Voce nao tem permissao para acessar os dados de outro usuario.");
        }

        return user;
    }

    private UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getDataCriacao()
        );
    }
}
