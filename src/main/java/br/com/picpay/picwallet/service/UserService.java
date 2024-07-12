package br.com.picpay.picwallet.service;

import br.com.picpay.picwallet.controller.user.dto.UserRequestDto;
import br.com.picpay.picwallet.controller.user.dto.UserResponseDto;
import br.com.picpay.picwallet.domain.entity.User;
import br.com.picpay.picwallet.domain.mapper.UserMapper;
import br.com.picpay.picwallet.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserService(final UserRepository userRepository, final UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public Mono<UserResponseDto> createUser(final UserRequestDto user) {
        return Mono.just(user)
                .map(mapper::toEntity)
                .flatMap(userRepository::save)
                .map(mapper::toDto);
    }

    public Mono<UserResponseDto> getUser(final Long id) {
        return userRepository.findById(id).map(mapper::toDto);
    }
}
