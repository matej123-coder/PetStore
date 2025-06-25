package com.example.pet.mapper;

import com.example.pet.domain.Cat;
import com.example.pet.domain.Dog;
import com.example.pet.domain.Pet;
import com.example.pet.domain.dto.PetDto;
import com.example.pet.domain.response.PetResponseDto;
import com.example.pet.exception.InvalidPetCreationException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {
    private final OwnerMapper ownerMapper;

    public PetMapper(@Lazy OwnerMapper ownerMapper) {
        this.ownerMapper = ownerMapper;
    }

    public Pet dtoToModel(PetDto dto) {
        Pet pet;

        if ("Dog".equals(dto.getType())) {
            Dog dog = new Dog();
            dog.setRating(dto.getRating());
            pet = dog;
        } else if ("Cat".equals(dto.getType())) {
            pet = new Cat();
        } else {
            throw new InvalidPetCreationException("Type must be 'Dog' or 'Cat'");
        }

        pet.setName(dto.getName());
        pet.setDescription(dto.getDescription());
        pet.setDateOfBirth(dto.getDateOfBirth());
        return pet;
    }

    public PetResponseDto modelToResponse(Pet pet) {
        PetResponseDto dto = new PetResponseDto();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setDescription(pet.getDescription());
        dto.setDateOfBirth(pet.getDateOfBirth());
        dto.setPrice(pet.getPrice());

        if (pet instanceof Dog dog) {
            dto.setType("Dog");
            dto.setRating(dog.getRating());
        } else {
            dto.setType("Cat");
            dto.setRating(null);
        }

        if (pet.getOwner() != null) {
            dto.setOwnerResponseDto(ownerMapper.modelToResponse(pet.getOwner()));
        }

        return dto;
    }
}
