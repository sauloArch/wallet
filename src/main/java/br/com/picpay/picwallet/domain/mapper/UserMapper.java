package br.com.picpay.picwallet.domain.mapper;

import br.com.picpay.picwallet.controller.user.dto.UserRequestDto;
import br.com.picpay.picwallet.controller.user.dto.UserResponseDto;
import br.com.picpay.picwallet.domain.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(final UserRequestDto dto){
        return UserEntity.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public UserResponseDto toDto(final UserEntity entity){
        return UserResponseDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }
}
