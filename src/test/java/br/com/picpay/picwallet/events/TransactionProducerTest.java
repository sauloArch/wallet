package br.com.picpay.picwallet.events;

import br.com.picpay.picwallet.domain.entity.TransactionEntity;
import br.com.picpay.picwallet.domain.enums.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransactionProducerTest {

    @Mock
    private KafkaTemplate<String, TransactionEntity> kafkaTemplate;

    @InjectMocks
    private TransactionProducer transactionProducer;

    @Test
    public void testSendTransactionWhenCalledThenTransactionSentToKafka() {

        TransactionEntity transactionEntity = TransactionEntity.builder()
                .id(1L)
                .userId(123L)
                .amount(BigDecimal.valueOf(100.00))
                .type(TransactionType.TRANSFER)
                .timestamp(LocalDateTime.now())
                .build();

        transactionProducer.sendTransaction(transactionEntity);

        verify(kafkaTemplate).send("transactions", transactionEntity);
    }
}
