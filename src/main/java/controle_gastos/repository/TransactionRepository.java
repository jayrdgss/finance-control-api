package controle_gastos.repository;

import controle_gastos.entity.Transaction;
import controle_gastos.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserIdAndTipo(Long userId, TransactionType tipo);

    List<Transaction> findByUserIdAndCategoryId(Long userId, Long categoryId);

    List<Transaction> findByUserIdAndDataBetween(Long userId, LocalDateTime dataInicial, LocalDateTime dataFinal);

    List<Transaction> findByUserIdAndTipoAndCategoryId(Long userId, TransactionType tipo, Long categoryId);

    List<Transaction> findByUserIdAndTipoAndDataBetween(
            Long userId,
            TransactionType tipo,
            LocalDateTime dataInicial,
            LocalDateTime dataFinal
    );

    List<Transaction> findByUserIdAndCategoryIdAndDataBetween(
            Long userId,
            Long categoryId,
            LocalDateTime dataInicial,
            LocalDateTime dataFinal
    );

    List<Transaction> findByUserIdAndTipoAndCategoryIdAndDataBetween(
            Long userId,
            TransactionType tipo,
            Long categoryId,
            LocalDateTime dataInicial,
            LocalDateTime dataFinal
    );
}
