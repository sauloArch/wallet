package br.com.picpay.picwallet.domain.repository;

import br.com.picpay.picwallet.domain.entity.Wallet;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface WalletRepository extends ReactiveCrudRepository<Wallet, Long> {
    Mono<Wallet> findByUserId(Long userId);
}
