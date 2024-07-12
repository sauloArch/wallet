package br.com.picpay.picwallet.domain.entity;

import br.com.picpay.picwallet.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@Table("transactions")
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {

    @Id
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDateTime timestamp;
}
