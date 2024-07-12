package br.com.picpay.picwallet.events;

import br.com.picpay.picwallet.domain.entity.Transaction;
import br.com.picpay.picwallet.service.TransactionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private final TransactionService transactionService;

    public KafkaConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = "transactions", groupId = "wallet-group")
    public void listen(Transaction transaction) {
        transactionService.createTransaction(transaction)
                .subscribe();
    }
}
