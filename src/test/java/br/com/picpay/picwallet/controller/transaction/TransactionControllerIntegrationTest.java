package br.com.picpay.picwallet.controller.transaction;

import br.com.picpay.picwallet.controller.transaction.dto.TransactionResponseDto;
import br.com.picpay.picwallet.domain.entity.TransactionEntity;
import br.com.picpay.picwallet.domain.enums.TransactionType;
import br.com.picpay.picwallet.domain.mapper.TransactionMapper;
import br.com.picpay.picwallet.domain.repository.TransactionRepository;
import br.com.picpay.picwallet.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = TransactionController.class)
@Import(TransactionService.class)
public class TransactionControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TransactionRepository repository;

    @MockBean
    private TransactionMapper mapper;

    private TransactionResponseDto transaction1;
    private TransactionResponseDto transaction2;

    @BeforeEach
    public void setUp() {
        transaction1 = TransactionResponseDto.builder()
                .id(1L)
                .userId(1L)
                .amount(BigDecimal.valueOf(100.00))
                .type(TransactionType.TRANSFER)
                .timestamp(LocalDateTime.now())
                .build();

        transaction2 = TransactionResponseDto.builder()
                .id(2L)
                .userId(1L)
                .amount(BigDecimal.valueOf(200.00))
                .type(TransactionType.TRANSFER)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Test
    public void testGetTransactions() {

        TransactionEntity entity = TransactionEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100.00))
                .timestamp(LocalDateTime.now())
                .userId(1L)
                .type(TransactionType.TRANSFER)
                .build();

        when(repository.findByUserIdOrderByTimestampDesc(1L))
                .thenReturn(Flux.just(entity));
        when(mapper.toDto(entity)).thenReturn(transaction1);

        webTestClient.get().uri("/transactions/1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TransactionResponseDto.class)
                .hasSize(1);
    }
}
