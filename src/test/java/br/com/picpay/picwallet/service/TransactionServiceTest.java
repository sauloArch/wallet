package br.com.picpay.picwallet.service;

import br.com.picpay.picwallet.controller.transaction.dto.TransactionResponseDto;
import br.com.picpay.picwallet.domain.entity.TransactionEntity;
import br.com.picpay.picwallet.domain.enums.TransactionType;
import br.com.picpay.picwallet.domain.mapper.TransactionMapper;
import br.com.picpay.picwallet.domain.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper mapper;

    @InjectMocks
    private TransactionService transactionService;

    private TransactionEntity transactionEntity;
    private TransactionResponseDto transactionResponseDto;

    @BeforeEach
    void setUp() {
        transactionEntity = TransactionEntity.builder()
                .id(1L)
                .userId(1L)
                .amount(BigDecimal.valueOf(100.00))
                .type(TransactionType.CREATE)
                .timestamp(LocalDateTime.now())
                .build();

        transactionResponseDto = TransactionResponseDto.builder()
                .id(1L)
                .userId(1L)
                .amount(BigDecimal.valueOf(100.00))
                .type(TransactionType.CREATE)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Test
    void testCreateTransactionWhenTransactionIsCreatedThenReturnTransaction() {
        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(Mono.just(transactionEntity));
        when(mapper.toDto(any(TransactionEntity.class))).thenReturn(transactionResponseDto);

        StepVerifier.create(transactionService.createTransaction(transactionEntity))
                .expectNextMatches(transaction -> transaction.getId().equals(1L))
                .verifyComplete();

        verify(transactionRepository, times(1)).save(any(TransactionEntity.class));
        verify(mapper, times(1)).toDto(any(TransactionEntity.class));
    }

    @Test
    void testGetTransactionsWhenUserHasTransactionsThenReturnTransactions() {
        when(transactionRepository.findByUserIdOrderByTimestampDesc(1L)).thenReturn(Flux.fromIterable(Arrays.asList(transactionEntity)));
        when(mapper.toDto(any(TransactionEntity.class))).thenReturn(transactionResponseDto);

        StepVerifier.create(transactionService.getTransactions(1L))
                .expectNextMatches(transaction -> transaction.getUserId().equals(1L))
                .verifyComplete();

        verify(transactionRepository, times(1)).findByUserIdOrderByTimestampDesc(1L);
        verify(mapper, times(1)).toDto(any(TransactionEntity.class));
    }

    @Test
    void testGetTransactionsWhenUserHasNoTransactionsThenReturnEmpty() {
        when(transactionRepository.findByUserIdOrderByTimestampDesc(1L)).thenReturn(Flux.empty());

        StepVerifier.create(transactionService.getTransactions(1L))
                .verifyComplete();

        verify(transactionRepository, times(1)).findByUserIdOrderByTimestampDesc(1L);
        verify(mapper, never()).toDto(any(TransactionEntity.class));
    }
}