package br.com.picpay.picwallet.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
@Table("wallets")
@AllArgsConstructor
@NoArgsConstructor
public class WalletEntity {

    @Id
    private Long id;
    private Long userId;
    private BigDecimal balance;
}
