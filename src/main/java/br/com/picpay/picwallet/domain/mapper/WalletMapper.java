package br.com.picpay.picwallet.domain.mapper;

import br.com.picpay.picwallet.controller.wallet.dto.WalletRequestDto;
import br.com.picpay.picwallet.controller.wallet.dto.WalletResponseDto;
import br.com.picpay.picwallet.domain.entity.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

    public Wallet toEntity(final WalletRequestDto dto){
        return Wallet.builder()
                .balance(dto.getBalance())
                .userId(dto.getUserId())
                .build();
    }

    public Wallet responseToEntity(final WalletResponseDto dto){
        return Wallet.builder()
                .id(dto.getId())
                .balance(dto.getBalance())
                .userId(dto.getUserId())
                .build();
    }

    public WalletResponseDto toDto(final Wallet entity){
        return WalletResponseDto.builder()
                .id(entity.getId())
                .balance(entity.getBalance())
                .userId(entity.getUserId())
                .build();
    }
}
