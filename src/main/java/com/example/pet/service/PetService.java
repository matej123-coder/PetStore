package com.example.pet.service;

import com.example.pet.domain.dto.PetDto;
import com.example.pet.domain.response.BuyHistoryLogResponse;
import com.example.pet.domain.response.PetResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PetService {
    ResponseEntity<List<PetResponseDto>> listPet();

    ResponseEntity<List<PetResponseDto>> createPet(List<PetDto> petDtos);

    ResponseEntity<List<String>> buyPets();

    ResponseEntity<List<BuyHistoryLogResponse>> showHistoryLogs();
}
