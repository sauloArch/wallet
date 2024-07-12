package br.com.picpay.picwallet.service;

import br.com.picpay.picwallet.controller.wallet.dto.WalletResponseDto;
import br.com.picpay.picwallet.domain.entity.Transaction;
import br.com.picpay.picwallet.domain.entity.Wallet;
import br.com.picpay.picwallet.domain.mapper.WalletMapper;
import br.com.picpay.picwallet.domain.repository.WalletRepository;
import br.com.picpay.picwallet.events.KafkaProducer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static br.com.picpay.picwallet.domain.enums.TransactionType.CREATE;
import static br.com.picpay.picwallet.domain.enums.TransactionType.TRANSFER;
import static br.com.picpay.picwallet.domain.enums.TransactionType.WITHDRAW;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper mapper;
    private final KafkaProducer producer;

    public WalletService(WalletRepository walletRepository, WalletMapper mapper, KafkaProducer producer) {
        this.walletRepository = walletRepository;
        this.mapper = mapper;
        this.producer = producer;
    }

    public Mono<WalletResponseDto> createWallet(final Long userId) {
        return Mono.just(Wallet.builder()
                        .userId(userId)
                        .balance(BigDecimal.ZERO)
                        .build())
                .flatMap(walletRepository::save)
                .map(mapper::toDto)
                .doOnSuccess(res -> producer.sendTransaction(Transaction.builder()
                                .timestamp(LocalDateTime.now())
                                .type(CREATE)
                                .amount(BigDecimal.ZERO)
                                .userId(userId)
                        .build()));
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
                .map(mapper::toDto)
                .doOnSuccess(res -> producer.sendTransaction(Transaction.builder()
                        .timestamp(LocalDateTime.now())
                        .type(TRANSFER)
                        .amount(amount)
                        .userId(userId)
                        .build()));
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
                .map(mapper::toDto)
                .doOnSuccess(res -> producer.sendTransaction(Transaction.builder()
                        .timestamp(LocalDateTime.now())
                        .type(WITHDRAW)
                        .amount(amount)
                        .userId(userId)
                        .build()));
    }
}
