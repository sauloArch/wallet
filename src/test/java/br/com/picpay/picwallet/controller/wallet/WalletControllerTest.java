package br.com.picpay.picwallet.controller.wallet;

import br.com.picpay.picwallet.controller.wallet.dto.WalletResponseDto;
import br.com.picpay.picwallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    private WalletResponseDto walletResponseDto;

    @BeforeEach
    void setUp() {
        walletResponseDto = WalletResponseDto.builder()
                .id(1L)
                .userId(1L)
                .balance(BigDecimal.valueOf(100.00))
                .build();
    }

    @Test
    public void testCreateWalletWhenCalledThenReturnWalletResponseDto() {
        when(walletService.createWallet(any(Long.class))).thenReturn(Mono.just(walletResponseDto));

        Mono<ResponseEntity<WalletResponseDto>> response = walletController.createWallet(1L);

        StepVerifier.create(response)
                .assertNext(res -> {
                    assertThat(res.getStatusCodeValue()).isEqualTo(200);
                    assertThat(res.getBody()).isEqualTo(walletResponseDto);
                })
                .verifyComplete();
    }

    @Test
    public void testGetWalletWhenCalledThenReturnWalletResponseDto() {
        when(walletService.getWallet(any(Long.class))).thenReturn(Mono.just(walletResponseDto));

        Mono<ResponseEntity<WalletResponseDto>> response = walletController.getWallet(1L);

        StepVerifier.create(response)
                .assertNext(res -> {
                    assertThat(res.getStatusCodeValue()).isEqualTo(200);
                    assertThat(res.getBody()).isEqualTo(walletResponseDto);
                })
                .verifyComplete();
    }

    @Test
    public void testGetWalletWhenWalletNotFoundThenReturnNotFound() {
        when(walletService.getWallet(any(Long.class))).thenReturn(Mono.empty());

        Mono<ResponseEntity<WalletResponseDto>> response = walletController.getWallet(1L);

        StepVerifier.create(response)
                .assertNext(res -> assertThat(res.getStatusCodeValue()).isEqualTo(404))
                .verifyComplete();
    }

    @Test
    public void testDepositWhenCalledThenReturnUpdatedWalletResponseDto() {
        when(walletService.deposit(eq(1L), any(BigDecimal.class))).thenReturn(Mono.just(walletResponseDto));

        Mono<ResponseEntity<WalletResponseDto>> response = walletController.deposit(1L, BigDecimal.valueOf(50.00));

        StepVerifier.create(response)
                .assertNext(res -> {
                    assertThat(res.getStatusCodeValue()).isEqualTo(200);
                    assertThat(res.getBody()).isEqualTo(walletResponseDto);
                })
                .verifyComplete();
    }

    @Test
    public void testWithdrawWhenCalledThenReturnUpdatedWalletResponseDto() {
        when(walletService.withdraw(eq(1L), any(BigDecimal.class))).thenReturn(Mono.just(walletResponseDto));

        Mono<ResponseEntity<WalletResponseDto>> response = walletController.withdraw(1L, BigDecimal.valueOf(50.00));

        StepVerifier.create(response)
                .assertNext(res -> {
                    assertThat(res.getStatusCodeValue()).isEqualTo(200);
                    assertThat(res.getBody()).isEqualTo(walletResponseDto);
                })
                .verifyComplete();
    }
}
