package br.com.picpay.picwallet.domain.mapper;

import br.com.picpay.picwallet.controller.transaction.dto.TransactionResponseDto;
import br.com.picpay.picwallet.domain.entity.TransactionEntity;
import br.com.picpay.picwallet.domain.enums.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TransactionMapperTest {

    @InjectMocks
    private TransactionMapper transactionMapper;

    @Test
    public void testToDtoWhenEntityProvidedThenDtoReturnedWithSameValues() {
        TransactionEntity entity = TransactionEntity.builder()
                .id(1L)
                .userId(100L)
                .amount(new BigDecimal("150.00"))
                .type(TransactionType.CREATE)
                .timestamp(LocalDateTime.now())
                .build();

        TransactionResponseDto dto = transactionMapper.toDto(entity);

        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getUserId()).isEqualTo(entity.getUserId());
        assertThat(dto.getAmount()).isEqualTo(entity.getAmount());
        assertThat(dto.getType()).isEqualTo(entity.getType());
        assertThat(dto.getTimestamp()).isEqualTo(entity.getTimestamp());
    }

    @Test
    public void testToDtoWhenEntityWithNullValuesProvidedThenDtoReturnedWithNullValues() {
        TransactionEntity entity = TransactionEntity.builder()
                .id(null)
                .userId(null)
                .amount(null)
                .type(null)
                .timestamp(null)
                .build();

        TransactionResponseDto dto = transactionMapper.toDto(entity);

        assertThat(dto.getId()).isNull();
        assertThat(dto.getUserId()).isNull();
        assertThat(dto.getAmount()).isNull();
        assertThat(dto.getType()).isNull();
        assertThat(dto.getTimestamp()).isNull();
    }
}