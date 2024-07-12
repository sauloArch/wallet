package br.com.picpay.picwallet.service;

import br.com.picpay.picwallet.controller.wallet.dto.WalletResponseDto;
import br.com.picpay.picwallet.domain.entity.WalletEntity;
import br.com.picpay.picwallet.domain.mapper.WalletMapper;
import br.com.picpay.picwallet.domain.repository.WalletRepository;
import br.com.picpay.picwallet.events.TransactionProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletMapper mapper;

    @Mock
    private TransactionProducer producer;

    @InjectMocks
    private WalletService walletService;

    private WalletEntity walletEntity;
    private WalletResponseDto walletResponseDto;

    @BeforeEach
    void setUp() {
        walletEntity = WalletEntity.builder()
                .id(1L)
                .userId(1L)
                .balance(BigDecimal.ZERO)
                .build();

        walletResponseDto = WalletResponseDto.builder()
                .id(1L)
                .userId(1L)
                .balance(BigDecimal.ZERO)
                .build();
    }

    @Test
    void testCreateWalletWhenWalletIsCreatedThenReturnWalletWithZeroBalance() {
        when(walletRepository.save(any(WalletEntity.class))).thenReturn(Mono.just(walletEntity));
        when(mapper.toDto(any(WalletEntity.class))).thenReturn(walletResponseDto);

        StepVerifier.create(walletService.createWallet(1L))
                .expectNextMatches(wallet -> wallet.getBalance().compareTo(BigDecimal.ZERO) == 0)
                .verifyComplete();

        verify(walletRepository, times(1)).save(any(WalletEntity.class));
        verify(mapper, times(1)).toDto(any(WalletEntity.class));
        verify(producer, times(1)).sendTransaction(any());
    }

    @Test
    void testGetWalletWhenWalletIsReturnedThenReturnWallet() {
        when(walletRepository.findByUserId(1L)).thenReturn(Mono.just(walletEntity));
        when(mapper.toDto(any(WalletEntity.class))).thenReturn(walletResponseDto);

        StepVerifier.create(walletService.getWallet(1L))
                .expectNextMatches(wallet -> wallet.getUserId().equals(1L))
                .verifyComplete();

        verify(walletRepository, times(1)).findByUserId(1L);
        verify(mapper, times(1)).toDto(any(WalletEntity.class));
    }

    @Test
    void testDepositWhenAmountIsDepositedThenReturnWalletWithIncreasedBalance() {
        BigDecimal depositAmount = BigDecimal.valueOf(100);
        WalletEntity updatedWalletEntity = WalletEntity.builder()
                .id(1L)
                .userId(1L)
                .balance(depositAmount)
                .build();
        WalletResponseDto updatedWalletResponseDto = WalletResponseDto.builder()
                .id(1L)
                .userId(1L)
                .balance(depositAmount)
                .build();

        when(walletRepository.findByUserId(1L)).thenReturn(Mono.just(walletEntity));
        when(walletRepository.save(any(WalletEntity.class))).thenReturn(Mono.just(updatedWalletEntity));
        when(mapper.toDto(any(WalletEntity.class))).thenReturn(updatedWalletResponseDto);
        when(mapper.responseToEntity(any(WalletResponseDto.class))).thenReturn(updatedWalletEntity);

        StepVerifier.create(walletService.deposit(1L, depositAmount))
                .expectNextMatches(Objects::nonNull)
                .verifyComplete();

        verify(walletRepository, times(1)).findByUserId(1L);
        verify(walletRepository, times(1)).save(any(WalletEntity.class));
    }

    @Test
    void testWithdrawWhenAmountIsWithdrawnThenReturnWalletWithDecreasedBalance() {
        BigDecimal initialBalance = BigDecimal.valueOf(50000);
        BigDecimal withdrawAmount = BigDecimal.valueOf(100);
        WalletEntity initialWalletEntity = WalletEntity.builder()
                .id(1L)
                .userId(1L)
                .balance(initialBalance)
                .build();
        WalletResponseDto initialWalletResponseDto = WalletResponseDto.builder()
                .id(1L)
                .userId(1L)
                .balance(initialBalance)
                .build();
        WalletEntity updatedWalletEntity = WalletEntity.builder()
                .id(1L)
                .userId(1L)
                .balance(initialBalance.subtract(withdrawAmount))
                .build();
        WalletResponseDto updatedWalletResponseDto = WalletResponseDto.builder()
                .id(1L)
                .userId(1L)
                .balance(initialBalance.subtract(withdrawAmount))
                .build();

        when(walletRepository.findByUserId(1L)).thenReturn(Mono.just(initialWalletEntity));
        when(walletRepository.save(any(WalletEntity.class))).thenReturn(Mono.just(updatedWalletEntity));
        when(mapper.toDto(any(WalletEntity.class))).thenReturn(updatedWalletResponseDto);
        when(mapper.responseToEntity(any(WalletResponseDto.class))).thenReturn(updatedWalletEntity);

        StepVerifier.create(walletService.withdraw(1L, withdrawAmount))
                .expectNextMatches(Objects::nonNull)
                .verifyComplete();

        verify(walletRepository, times(1)).findByUserId(1L);
        verify(walletRepository, times(1)).save(any(WalletEntity.class));
        verify(producer, times(1)).sendTransaction(any());
    }
}
