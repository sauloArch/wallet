package br.com.picpay.picwallet.domain.repository;

import br.com.picpay.picwallet.domain.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {
}
