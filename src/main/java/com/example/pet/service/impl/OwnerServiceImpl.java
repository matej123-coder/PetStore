package com.example.pet.service.impl;

import com.example.pet.domain.Owner;
import com.example.pet.domain.dto.OwnerDto;
import com.example.pet.domain.response.OwnerResponseDto;
import com.example.pet.exception.InvalidUserCreationException;
import com.example.pet.mapper.OwnerMapper;
import com.example.pet.repository.OwnerRepository;
import com.example.pet.service.OwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;
    public OwnerServiceImpl(OwnerRepository ownerRepository, OwnerMapper ownerMapper) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = ownerMapper;
    }
    @Override
    public ResponseEntity<List<OwnerResponseDto>> createOwners(List<OwnerDto> ownerDtos) {
        boolean hasPets= ownerDtos.stream().anyMatch(dto->dto.getPetDtos()!=null && !dto.getPetDtos().isEmpty());
        if(hasPets){
            throw new InvalidUserCreationException("Users must not have pets at creation. Pet list must be empty.");
        }
        List<Owner> owners= ownerDtos.stream().map(ownerMapper::dtoToModel).toList();
        ownerRepository.saveAll(owners);
        List<OwnerResponseDto> ownerResponseDtos = owners.stream().map(ownerMapper::modelToResponse).toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerResponseDtos);
    }

    @Override
    public ResponseEntity<List<OwnerResponseDto>> listOwners() {
        List<Owner> owners=ownerRepository.findAll();
        List<OwnerResponseDto> ownerResponseDtos=owners.stream().map(ownerMapper::modelToResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ownerResponseDtos);
    }

}
