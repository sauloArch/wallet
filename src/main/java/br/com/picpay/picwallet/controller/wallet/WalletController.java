package br.com.picpay.picwallet.controller.wallet;

import br.com.picpay.picwallet.controller.wallet.dto.WalletResponseDto;
import br.com.picpay.picwallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/{userId}")
    public Mono<ResponseEntity<WalletResponseDto>> createWallet(@PathVariable Long userId) {
        return walletService.createWallet(userId)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{userId}")
    public Mono<ResponseEntity<WalletResponseDto>> getWallet(@PathVariable Long userId) {
        return walletService.getWallet(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}/deposit")
    public Mono<ResponseEntity<WalletResponseDto>> deposit(@PathVariable Long userId, @RequestBody BigDecimal amount) {
        return walletService.deposit(userId, amount)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/{userId}/withdraw")
    public Mono<ResponseEntity<WalletResponseDto>> withdraw(@PathVariable Long userId, @RequestBody BigDecimal amount) {
        return walletService.withdraw(userId, amount)
                .map(ResponseEntity::ok);
    }
}
