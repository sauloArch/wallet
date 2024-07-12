package br.com.picpay.picwallet.domain.mapper;

import br.com.picpay.picwallet.controller.wallet.dto.WalletRequestDto;
import br.com.picpay.picwallet.controller.wallet.dto.WalletResponseDto;
import br.com.picpay.picwallet.domain.entity.WalletEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class WalletMapperTest {

    @InjectMocks
    private WalletMapper walletMapper;

    @BeforeEach
    void setUp() {
        walletMapper = new WalletMapper();
    }

    @Test
    public void testToEntityWhenGivenWalletRequestDtoThenReturnWalletEntity() {
        WalletRequestDto walletRequestDto = WalletRequestDto.builder()
                .userId(1L)
                .balance(new BigDecimal("100.00"))
                .build();

        WalletEntity walletEntity = walletMapper.toEntity(walletRequestDto);

        assertThat(walletEntity).isNotNull();
        assertThat(walletEntity.getUserId()).isEqualTo(walletRequestDto.getUserId());
        assertThat(walletEntity.getBalance()).isEqualTo(walletRequestDto.getBalance());
    }

    @Test
    public void testResponseToEntityWhenGivenWalletResponseDtoThenReturnWalletEntity() {
        WalletResponseDto walletResponseDto = WalletResponseDto.builder()
                .id(1L)
                .userId(2L)
                .balance(new BigDecimal("200.00"))
                .build();

        WalletEntity walletEntity = walletMapper.responseToEntity(walletResponseDto);

        assertThat(walletEntity).isNotNull();
        assertThat(walletEntity.getId()).isEqualTo(walletResponseDto.getId());
        assertThat(walletEntity.getUserId()).isEqualTo(walletResponseDto.getUserId());
        assertThat(walletEntity.getBalance()).isEqualTo(walletResponseDto.getBalance());
    }

    @Test
    public void testToDtoWhenGivenWalletEntityThenReturnWalletResponseDto() {
        WalletEntity walletEntity = WalletEntity.builder()
                .id(1L)
                .userId(3L)
                .balance(new BigDecimal("300.00"))
                .build();

        WalletResponseDto walletResponseDto = walletMapper.toDto(walletEntity);

        assertThat(walletResponseDto).isNotNull();
        assertThat(walletResponseDto.getId()).isEqualTo(walletEntity.getId());
        assertThat(walletResponseDto.getUserId()).isEqualTo(walletEntity.getUserId());
        assertThat(walletResponseDto.getBalance()).isEqualTo(walletEntity.getBalance());
    }
}
