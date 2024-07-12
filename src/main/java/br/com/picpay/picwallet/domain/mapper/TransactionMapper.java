package br.com.picpay.picwallet.domain.mapper;

import br.com.picpay.picwallet.controller.transaction.dto.TransactionResponseDto;
import br.com.picpay.picwallet.domain.entity.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionResponseDto toDto(final TransactionEntity entity){
        return TransactionResponseDto.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .userId(entity.getUserId())
                .timestamp(entity.getTimestamp())
                .type(entity.getType())
                .build();
    }
}
