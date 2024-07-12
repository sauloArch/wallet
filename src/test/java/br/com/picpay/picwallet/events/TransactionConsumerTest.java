package br.com.picpay.picwallet.events;

import br.com.picpay.picwallet.controller.transaction.dto.TransactionResponseDto;
import br.com.picpay.picwallet.domain.entity.TransactionEntity;
import br.com.picpay.picwallet.domain.enums.TransactionType;
import br.com.picpay.picwallet.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionConsumerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionConsumer transactionConsumer;

    private TransactionEntity transactionEntity;

    @BeforeEach
    void setUp() {
        transactionEntity = TransactionEntity.builder()
                .id(1L)
                .userId(1L)
                .amount(BigDecimal.valueOf(100.00))
                .type(TransactionType.CREATE)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Test
    void testListenWhenTransactionIsReceivedThenCreateTransaction() {
        Mono<TransactionResponseDto> transactionEntityMono = Mono.just(TransactionResponseDto.builder()
                .id(1L)
                .userId(1L)
                .amount(BigDecimal.valueOf(100.00))
                .type(TransactionType.CREATE)
                .timestamp(LocalDateTime.now())
                .build());

        when(transactionService.createTransaction(any(TransactionEntity.class))).thenReturn(transactionEntityMono);

        transactionConsumer.listen(transactionEntity);

        verify(transactionService).createTransaction(any(TransactionEntity.class));
    }

    @Test
    void testListenWhenTransactionIsReceivedThenSubscribeToMono() {
        Mono<TransactionResponseDto> transactionEntityMono = Mono.just(TransactionResponseDto.builder()
                .id(1L)
                .userId(1L)
                .amount(BigDecimal.valueOf(100.00))
                .type(TransactionType.CREATE)
                .timestamp(LocalDateTime.now())
                .build());

        when(transactionService.createTransaction(any(TransactionEntity.class))).thenReturn(transactionEntityMono);

        transactionConsumer.listen(transactionEntity);

        StepVerifier.create(transactionService.createTransaction(transactionEntity))
                .expectNextMatches(transaction -> transaction.getId().equals(1L))
                .verifyComplete();
    }
}
