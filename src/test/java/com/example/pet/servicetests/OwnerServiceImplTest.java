package com.example.pet.servicetests;

import com.example.pet.domain.Owner;
import com.example.pet.domain.dto.OwnerDto;
import com.example.pet.domain.dto.PetDto;
import com.example.pet.domain.response.OwnerResponseDto;
import com.example.pet.exception.InvalidUserCreationException;
import com.example.pet.mapper.OwnerMapper;
import com.example.pet.repository.OwnerRepository;
import com.example.pet.service.impl.OwnerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceImplTest {
    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private OwnerMapper ownerMapper;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @Test
    void testCreateOwners_success() {
        OwnerDto dto = new OwnerDto();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("john@example.com");
        dto.setBudget(100.0);
        dto.setPetDtos(Collections.emptyList());

        Owner owner = new Owner();
        OwnerResponseDto responseDto = new OwnerResponseDto();

        when(ownerMapper.dtoToModel(dto)).thenReturn(owner);
        when(ownerMapper.modelToResponse(owner)).thenReturn(responseDto);

        ResponseEntity<List<OwnerResponseDto>> result = ownerService.createOwners(List.of(dto));

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void testCreateOwners_failsWhenHasPets() {
        OwnerDto dto = new OwnerDto();
        dto.setPetDtos(List.of(new PetDto()));

        assertThrows(InvalidUserCreationException.class, () -> ownerService.createOwners(List.of(dto)));
    }

    @Test
    void testListOwners_success() {
        Owner owner = new Owner();
        OwnerResponseDto responseDto = new OwnerResponseDto();

        when(ownerRepository.findAll()).thenReturn(List.of(owner));
        when(ownerMapper.modelToResponse(owner)).thenReturn(responseDto);

        ResponseEntity<List<OwnerResponseDto>> result = ownerService.listOwners();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
    }
}
