package br.com.picpay.picwallet.service;

import br.com.picpay.picwallet.controller.transaction.dto.TransactionResponseDto;
import br.com.picpay.picwallet.domain.entity.Transaction;
import br.com.picpay.picwallet.domain.enums.TransactionType;
import br.com.picpay.picwallet.domain.mapper.TransactionMapper;
import br.com.picpay.picwallet.domain.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final  TransactionRepository transactionRepository;
    private final TransactionMapper mapper;

    public TransactionService(TransactionRepository transactionRepository, TransactionMapper mapper) {
        this.transactionRepository = transactionRepository;
        this.mapper = mapper;
    }

    public Mono<TransactionResponseDto> createTransaction(final Long userId, final BigDecimal amount, final String type) {
        return Mono.just(Transaction.builder()
                        .userId(userId)
                        .amount(amount)
                        .type(TransactionType.valueOf(type))
                        .timestamp(LocalDateTime.now())
                        .build())
                .flatMap(transactionRepository::save)
                .map(mapper::toDto);
    }

    public Flux<TransactionResponseDto> getTransactions(final Long userId) {
        return transactionRepository.findByUserIdOrderByTimestampDesc(userId).map(mapper::toDto);
    }
}
