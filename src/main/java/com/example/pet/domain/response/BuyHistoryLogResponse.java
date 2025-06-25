package com.example.pet.domain.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BuyHistoryLogResponse {
    private Long id;
    private LocalDateTime executionDate;
    private int successCount;
    private int failureCount;
}
