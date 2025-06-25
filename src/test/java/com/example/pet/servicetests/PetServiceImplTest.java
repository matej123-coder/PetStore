package com.example.pet.servicetests;

import com.example.pet.domain.*;
import com.example.pet.domain.dto.PetDto;
import com.example.pet.domain.response.BuyHistoryLogResponse;
import com.example.pet.domain.response.PetResponseDto;
import com.example.pet.exception.InvalidPetCreationException;
import com.example.pet.mapper.BuyHistoryLogMapper;
import com.example.pet.mapper.PetMapper;
import com.example.pet.repository.BuyHistoryLogRepository;
import com.example.pet.repository.OwnerRepository;
import com.example.pet.repository.PetRepository;
import com.example.pet.service.impl.PetServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class PetServiceImplTest {
    @Mock
    private PetRepository petRepository;

    @Mock
    private PetMapper petMapper;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private BuyHistoryLogRepository buyHistoryLogRepository;

    @Mock
    private BuyHistoryLogMapper buyHistoryLogMapper;

    @InjectMocks
    private PetServiceImpl petService;

    @Test
    void testListPets_success() {
        Pet pet = new Cat();
        PetResponseDto responseDto = new PetResponseDto();

        when(petRepository.findAll()).thenReturn(List.of(pet));
        when(petMapper.modelToResponse(pet)).thenReturn(responseDto);

        ResponseEntity<List<PetResponseDto>> result = petService.listPet();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void testCreatePets_successWithCatAndDog() {
        PetDto catDto = new PetDto("Whiskers", "Cat", "Fluffy", LocalDate.now().minusYears(3), null);
        PetDto dogDto = new PetDto("Rex", "Dog", "Loyal", LocalDate.now().minusYears(2), 5);

        Cat cat = new Cat();
        Dog dog = new Dog();
        dog.setRating(5);
        cat.setDateOfBirth(catDto.getDateOfBirth());
        dog.setDateOfBirth(dogDto.getDateOfBirth());
        when(petMapper.dtoToModel(catDto)).thenReturn(cat);
        when(petMapper.dtoToModel(dogDto)).thenReturn(dog);

        ResponseEntity<List<PetResponseDto>> result = petService.createPet(List.of(catDto, dogDto));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(petRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testCreatePets_invalidType_throwsException() {
        PetDto invalidDto = new PetDto("Unknown", "???", "Bird", LocalDate.now(), null);
        assertThrows(InvalidPetCreationException.class, () -> petService.createPet(List.of(invalidDto)));
    }

    @Test
    void testBuyPets_successAndFailureLogged() {
        Owner ownerWithMoney = new Owner(1L, "John", "Doe", "john@example.com", 10.0, new ArrayList<>());
        Owner ownerNoMoney = new Owner(2L, "Jane", "Doe", "jane@example.com", 0.0, new ArrayList<>());
        Cat pet = new Cat();
        pet.setPrice(5.0);
        pet.setDateOfBirth(LocalDate.now().minusYears(1));
        pet.setName("Whiskers");

        when(ownerRepository.findAll()).thenReturn(List.of(ownerWithMoney, ownerNoMoney));
        when(petRepository.findByOwnerIsNull()).thenReturn(new ArrayList<>(List.of(pet)));

        ResponseEntity<List<String>> result = petService.buyPets();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size()); // Only one pet was bought
        verify(buyHistoryLogRepository).save(any(BuyHistoryLog.class));
    }

    @Test
    void testShowHistoryLogs_success() {
        BuyHistoryLog log = new BuyHistoryLog();
        BuyHistoryLogResponse response = new BuyHistoryLogResponse();

        when(buyHistoryLogRepository.findAll()).thenReturn(List.of(log));
        when(buyHistoryLogMapper.modelToResponse(log)).thenReturn(response);

        ResponseEntity<List<BuyHistoryLogResponse>> result = petService.showHistoryLogs();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
    }
}
