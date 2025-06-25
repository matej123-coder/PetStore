package com.example.pet.mapper;

import com.example.pet.domain.BuyHistoryLog;
import com.example.pet.domain.response.BuyHistoryLogResponse;
import org.springframework.stereotype.Component;

@Component
public class BuyHistoryLogMapper {
    public BuyHistoryLogResponse modelToResponse(BuyHistoryLog buyHistoryLog){
        BuyHistoryLogResponse buyHistoryLogResponse = new BuyHistoryLogResponse();
        buyHistoryLogResponse.setId(buyHistoryLog.getId());
        buyHistoryLogResponse.setExecutionDate(buyHistoryLog.getExecutionDate());
        buyHistoryLogResponse.setFailureCount(buyHistoryLog.getFailureCount());
        buyHistoryLogResponse.setSuccessCount(buyHistoryLog.getSuccessCount());
        return buyHistoryLogResponse;
    }
}
