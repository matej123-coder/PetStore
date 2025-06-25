package com.example.pet.repository;

import com.example.pet.domain.BuyHistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyHistoryLogRepository extends JpaRepository<BuyHistoryLog, Long> {
}
