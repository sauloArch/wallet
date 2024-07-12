package br.com.picpay.picwallet.domain.repository;

import br.com.picpay.picwallet.domain.entity.WalletEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface WalletRepository extends ReactiveCrudRepository<WalletEntity, Long> {
    Mono<WalletEntity> findByUserId(Long userId);
}
