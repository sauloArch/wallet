package br.com.picpay.picwallet.service;

import br.com.picpay.picwallet.controller.transaction.dto.TransactionResponseDto;
import br.com.picpay.picwallet.domain.entity.Transaction;
import br.com.picpay.picwallet.domain.mapper.TransactionMapper;
import br.com.picpay.picwallet.domain.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    private final  TransactionRepository transactionRepository;
    private final TransactionMapper mapper;

    public TransactionService(TransactionRepository transactionRepository, TransactionMapper mapper) {
        this.transactionRepository = transactionRepository;
        this.mapper = mapper;
    }

    public Mono<TransactionResponseDto> createTransaction(final Transaction transaction) {
        return transactionRepository.save(transaction)
                .map(mapper::toDto);
    }

    public Flux<TransactionResponseDto> getTransactions(final Long userId) {
        return transactionRepository.findByUserIdOrderByTimestampDesc(userId).map(mapper::toDto);
    }
}
