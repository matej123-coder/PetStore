package com.example.pet.service.impl;

import com.example.pet.domain.*;
import com.example.pet.domain.dto.PetDto;
import com.example.pet.domain.response.BuyHistoryLogResponse;
import com.example.pet.domain.response.PetResponseDto;
import com.example.pet.exception.InvalidBuyException;
import com.example.pet.exception.InvalidPetCreationException;
import com.example.pet.mapper.BuyHistoryLogMapper;
import com.example.pet.mapper.PetMapper;
import com.example.pet.repository.BuyHistoryLogRepository;
import com.example.pet.repository.OwnerRepository;
import com.example.pet.repository.PetRepository;
import com.example.pet.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final OwnerRepository ownerRepository;
    private final BuyHistoryLogRepository buyHistoryLogRepository;
    private final BuyHistoryLogMapper buyHistoryLogMapper;

    public PetServiceImpl(PetRepository petRepository, PetMapper petMapper, OwnerRepository ownerRepository, BuyHistoryLogRepository buyHistoryLogRepository, BuyHistoryLogMapper buyHistoryLogMapper) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
        this.ownerRepository = ownerRepository;
        this.buyHistoryLogRepository = buyHistoryLogRepository;
        this.buyHistoryLogMapper = buyHistoryLogMapper;
    }

    @Override
    public ResponseEntity<List<PetResponseDto>> listPet() {
        List<Pet> pets = petRepository.findAll();
        List<PetResponseDto> petResponseDtos = pets.stream().map(petMapper::modelToResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(petResponseDtos);
    }

    @Override
    public ResponseEntity<List<PetResponseDto>> createPet(List<PetDto> petDtos) {
        List<Pet> pets = new ArrayList<>();
        for (PetDto dto : petDtos) {
            String type = dto.getType();

            if (!"Cat".equals(type) && !"Dog".equals(type)) {
                throw new InvalidPetCreationException("Only 'Cat' or 'Dog' types are allowed.");
            }
            if ("Cat".equals(type) && dto.getRating() != null) {
                throw new InvalidPetCreationException("Cats should not have a rating.");
            }

            if ("Dog".equals(type)) {
                if (dto.getRating() == null) {
                    throw new InvalidPetCreationException("Dogs must have a rating.");
                }
                if (dto.getRating() < 0 || dto.getRating() > 10) {
                    throw new InvalidPetCreationException("Dog rating must be between 0 and 10.");
                }
            }

            Pet pet = petMapper.dtoToModel(dto);
            long age = ChronoUnit.YEARS.between(pet.getDateOfBirth(), LocalDate.now());

            double price = "Dog".equals(type)
                    ? age + ((Dog) pet).getRating()
                    : age;

            pet.setPrice(price);
            pet.setOwner(null);

            pets.add(pet);
        }
        petRepository.saveAll(pets);
        List<PetResponseDto> response = pets.stream()
                .map(petMapper::modelToResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<String>> buyPets() {
        List<Owner> owners = ownerRepository.findAll();
        List<Pet> availablePets = petRepository.findByOwnerIsNull();
        List<String> messages = new ArrayList<>();
        int successCount = 0;
        int failureCount = 0;
        for (Owner owner : owners) {
            try {
                Optional<Pet> affordablePet = availablePets
                        .stream()
                        .filter(pet -> owner.getBudget() >= pet.getPrice())
                        .findFirst();
                if (affordablePet.isEmpty()) {
                    throw new InvalidBuyException("You do not have money to buy any pet");
                }
                Pet pet = affordablePet.get();
                pet.setOwner(owner);
                owner.setBudget(owner.getBudget() - pet.getPrice());
                availablePets.remove(pet);
                if (pet instanceof Cat) {
                    messages.add("Meow, cat " + pet.getName() + " has owner " + owner.getFirstName());
                } else if (pet instanceof Dog) {
                    messages.add("Woof, dog " + pet.getName() + " has owner " + owner.getFirstName());
                }
                successCount++;

            } catch (InvalidBuyException exception) {
                failureCount++;
            }
        }
        ownerRepository.saveAll(owners);
        petRepository.saveAll(
                owners.stream()
                        .flatMap(o -> o.getPets().stream())
                        .collect(Collectors.toList())
        );
        BuyHistoryLog buyHistoryLog = new BuyHistoryLog();
        buyHistoryLog.setExecutionDate(LocalDateTime.now());
        buyHistoryLog.setFailureCount(failureCount);
        buyHistoryLog.setSuccessCount(successCount);
        buyHistoryLogRepository.save(buyHistoryLog);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @Override
    public ResponseEntity<List<BuyHistoryLogResponse>> showHistoryLogs() {
        List<BuyHistoryLog> buyHistoryLogs = buyHistoryLogRepository.findAll();
        List<BuyHistoryLogResponse> buyHistoryLogResponses = buyHistoryLogs
                .stream()
                .map(buyHistoryLogMapper::modelToResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(buyHistoryLogResponses);
    }
}
