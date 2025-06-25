package com.example.pet.service;

import com.example.pet.domain.dto.OwnerDto;
import com.example.pet.domain.response.OwnerResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OwnerService {
    ResponseEntity<List<OwnerResponseDto>> createOwners(List<OwnerDto> ownerDtos);

    ResponseEntity<List<OwnerResponseDto>> listOwners();
}
