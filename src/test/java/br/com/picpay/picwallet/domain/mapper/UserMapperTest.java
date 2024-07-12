package br.com.picpay.picwallet.domain.mapper;

import br.com.picpay.picwallet.controller.user.dto.UserRequestDto;
import br.com.picpay.picwallet.domain.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    public void testToEntityWhenValidDtoThenReturnEntity() {
        UserRequestDto dto = UserRequestDto.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        UserEntity entity = userMapper.toEntity(dto);

        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getPassword(), entity.getPassword());
    }

    @Test
    public void testToEntityWhenNullDtoThenReturnEntityWithNullFields() {
        UserRequestDto dto = UserRequestDto.builder()
                .email(null)
                .password(null)
                .build();

        UserEntity entity = userMapper.toEntity(dto);

        assertNull(entity.getEmail());
        assertNull(entity.getPassword());
    }
}