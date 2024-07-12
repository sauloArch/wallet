package br.com.picpay.picwallet.controller.user;

import br.com.picpay.picwallet.controller.user.dto.UserRequestDto;
import br.com.picpay.picwallet.controller.user.dto.UserResponseDto;
import br.com.picpay.picwallet.domain.entity.UserEntity;
import br.com.picpay.picwallet.domain.mapper.UserMapper;
import br.com.picpay.picwallet.domain.repository.UserRepository;
import br.com.picpay.picwallet.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserController.class)
@Import(UserService.class)
public class UserControllerIntegrationTest {

    @MockBean
    private UserRepository repository;

    @MockBean
    private UserMapper mapper;

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testCreateUser() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("Joao")
                .email("joao@example.com")
                .password("123")
                .build();

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(1L)
                .name("Joao")
                .email("joao@example.com")
                .password("123")
                .build();

        UserEntity entity = UserEntity.builder()
                .id(1L)
                .name("Joao")
                .email("joao@example.com")
                .password("123")
                .build();

        when(mapper.toEntity(userRequestDto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.toDto(entity)).thenReturn(userResponseDto);

        webClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequestDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class);
    }

    @Test
    public void testGetUser() {
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();

        UserEntity entity = UserEntity.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();

        when(repository.findById(1L)).thenReturn(Mono.just(entity));
        when(mapper.toDto(entity)).thenReturn(userResponseDto);

        webClient.get().uri("/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class);
    }
}
