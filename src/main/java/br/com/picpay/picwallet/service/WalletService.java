package br.com.picpay.picwallet.service;

import br.com.picpay.picwallet.controller.wallet.dto.WalletResponseDto;
import br.com.picpay.picwallet.domain.entity.Wallet;
import br.com.picpay.picwallet.domain.mapper.WalletMapper;
import br.com.picpay.picwallet.domain.repository.WalletRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper mapper;

    public WalletService(WalletRepository walletRepository, WalletMapper mapper) {
        this.walletRepository = walletRepository;
        this.mapper = mapper;
    }

    public Mono<WalletResponseDto> createWallet(final Long userId) {
        return Mono.just(Wallet.builder()
                        .userId(userId)
                        .balance(BigDecimal.ZERO)
                        .build())
                .flatMap(walletRepository::save)
                .map(mapper::toDto);
    }

    public Mono<WalletResponseDto> getWallet(final Long userId) {
        return walletRepository.findByUserId(userId).map(mapper::toDto);
    }

    public Mono<WalletResponseDto> deposit(final Long userId, final BigDecimal amount) {
        return getWallet(userId)
                .map(wallet -> {
                    wallet.setBalance(wallet.getBalance().add(amount));
                    return mapper.responseToEntity(wallet);
                })
                .flatMap(walletRepository::save)
                .map(mapper::toDto);
    }

    public Mono<WalletResponseDto> withdraw(final Long userId, final BigDecimal amount) {
        return getWallet(userId)
                .filter(wall -> wall.getBalance().compareTo(amount) > 0)
                .switchIfEmpty(Mono.error(new RuntimeException("Insufficient funds")))
                .map(wallet -> {
                    wallet.setBalance(wallet.getBalance().subtract(amount));
                    return mapper.responseToEntity(wallet);
                })
                .flatMap(walletRepository::save)
                .map(mapper::toDto);
    }
}
