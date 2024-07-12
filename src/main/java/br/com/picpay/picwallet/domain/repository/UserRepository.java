package br.com.picpay.picwallet.domain.repository;

import br.com.picpay.picwallet.domain.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}
