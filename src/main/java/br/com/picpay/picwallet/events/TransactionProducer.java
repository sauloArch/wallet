package br.com.picpay.picwallet.events;

import br.com.picpay.picwallet.domain.entity.TransactionEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {

    private final KafkaTemplate<String, TransactionEntity> kafkaTemplate;

    public TransactionProducer(KafkaTemplate<String, TransactionEntity> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransaction(TransactionEntity transactionEntity) {
        kafkaTemplate.send("transactions", transactionEntity);
    }
}
