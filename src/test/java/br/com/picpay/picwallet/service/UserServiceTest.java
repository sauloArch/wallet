package br.com.picpay.picwallet.service;

import br.com.picpay.picwallet.controller.user.dto.UserRequestDto;
import br.com.picpay.picwallet.controller.user.dto.UserResponseDto;
import br.com.picpay.picwallet.domain.entity.UserEntity;
import br.com.picpay.picwallet.domain.mapper.UserMapper;
import br.com.picpay.picwallet.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUserWhenUserIsCreatedThenReturnUser() {

        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("password")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .email("john.doe@example.com")
                .password("password")
                .build();

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(1L)
                .email("john.doe@example.com")
                .password("password")
                .build();

        when(mapper.toEntity(any(UserRequestDto.class))).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(Mono.just(userEntity));
        when(mapper.toDto(any(UserEntity.class))).thenReturn(userResponseDto);

        Mono<UserResponseDto> result = userService.createUser(userRequestDto);

        StepVerifier.create(result)
                .expectNext(userResponseDto)
                .verifyComplete();
    }

    @Test
    public void testGetUserWhenUserExistsThenReturnUser() {

        Long userId = 1L;
        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .email("john.doe@example.com")
                .password("password")
                .build();

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(userId)
                .email("john.doe@example.com")
                .password("password")
                .build();

        when(userRepository.findById(userId)).thenReturn(Mono.just(userEntity));
        when(mapper.toDto(any(UserEntity.class))).thenReturn(userResponseDto);

        Mono<UserResponseDto> result = userService.getUser(userId);

        StepVerifier.create(result)
                .expectNext(userResponseDto)
                .verifyComplete();
    }

    @Test
    public void testGetUserWhenUserDoesNotExistThenReturnEmpty() {

        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Mono.empty());

        Mono<UserResponseDto> result = userService.getUser(userId);

        StepVerifier.create(result)
                .verifyComplete();
    }
}
