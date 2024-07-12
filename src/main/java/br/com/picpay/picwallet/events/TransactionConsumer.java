package br.com.picpay.picwallet.events;

import br.com.picpay.picwallet.domain.entity.TransactionEntity;
import br.com.picpay.picwallet.service.TransactionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {
    private final TransactionService transactionService;

    public TransactionConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = "transactions", groupId = "wallet-group")
    public void listen(TransactionEntity transactionEntity) {
        transactionService.createTransaction(transactionEntity)
                .subscribe();
    }
}
