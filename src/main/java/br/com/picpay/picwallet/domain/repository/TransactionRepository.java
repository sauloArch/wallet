package br.com.picpay.picwallet.domain.repository;

import br.com.picpay.picwallet.domain.entity.TransactionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveCrudRepository<TransactionEntity, Long> {
    Flux<TransactionEntity> findByUserIdOrderByTimestampDesc(Long userId);
}
