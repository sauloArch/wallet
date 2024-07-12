package br.com.picpay.picwallet.domain.mapper;

import br.com.picpay.picwallet.controller.wallet.dto.WalletRequestDto;
import br.com.picpay.picwallet.controller.wallet.dto.WalletResponseDto;
import br.com.picpay.picwallet.domain.entity.WalletEntity;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

    public WalletEntity toEntity(final WalletRequestDto dto){
        return WalletEntity.builder()
                .balance(dto.getBalance())
                .userId(dto.getUserId())
                .build();
    }

    public WalletEntity responseToEntity(final WalletResponseDto dto){
        return WalletEntity.builder()
                .id(dto.getId())
                .balance(dto.getBalance())
                .userId(dto.getUserId())
                .build();
    }

    public WalletResponseDto toDto(final WalletEntity entity){
        return WalletResponseDto.builder()
                .id(entity.getId())
                .balance(entity.getBalance())
                .userId(entity.getUserId())
                .build();
    }
}
