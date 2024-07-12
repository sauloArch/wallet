package br.com.picpay.picwallet.controller.transaction.dto;

import br.com.picpay.picwallet.domain.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponseDto {

    private Long id;
    private Long userId;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDateTime timestamp;
}
