package br.com.picpay.picwallet.domain.repository;

import br.com.picpay.picwallet.domain.entity.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Long> {
    Flux<Transaction> findByUserIdOrderByTimestampDesc(Long userId);
}
