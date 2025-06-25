package com.example.pet.mapper;

import com.example.pet.domain.Owner;
import com.example.pet.domain.dto.OwnerDto;
import com.example.pet.domain.response.OwnerResponseDto;
import com.example.pet.domain.response.PetResponseDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OwnerMapper {
    private final PetMapper petMapper;

    public OwnerMapper(@Lazy PetMapper petMapper) {
        this.petMapper = petMapper;
    }

    public Owner dtoToModel(OwnerDto dto) {
        Owner owner = new Owner();
        owner.setEmail(dto.getEmail());
        owner.setFirstName(dto.getFirstName());
        owner.setLastName(dto.getLastName());
        owner.setBudget(dto.getBudget());
//        if (dto.getPetDtos() != null && !dto.getPetDtos().isEmpty()) {
//            List<Pet> pets = dto.getPetDtos().stream()
//                    .map(petDto -> {
//                        Pet pet = petMapper.dtoToModel(petDto);
//                        pet.setOwner(owner);
//                        return pet;
//                    })
//                    .collect(Collectors.toList());
//            owner.setPets(pets);
//        }
        return owner;
    }

    public OwnerResponseDto modelToResponse(Owner owner) {
        OwnerResponseDto dto = new OwnerResponseDto();
        dto.setId(owner.getId());
        dto.setFirstName(owner.getFirstName());
        dto.setLastName(owner.getLastName());
        dto.setEmail(owner.getEmail());
        dto.setBudget(owner.getBudget());

        if (owner.getPets() != null) {
            List<PetResponseDto> petDtos = owner.getPets().stream()
                    .map(petMapper::modelToResponse)
                    .toList();
            dto.setPets(petDtos);
        } else {
            dto.setPets(List.of());
        }

        return dto;
    }
}
