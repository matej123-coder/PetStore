package com.example.pet.dataholder;

import com.example.pet.domain.Cat;
import com.example.pet.domain.Dog;
import com.example.pet.domain.Owner;
import com.example.pet.domain.Pet;
import com.example.pet.repository.OwnerRepository;
import com.example.pet.repository.PetRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitilizer {
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;

    public DataInitilizer(OwnerRepository ownerRepository, PetRepository petRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
    }

    @PostConstruct
    public void init() {
        if (ownerRepository.count() == 0 && petRepository.count() == 0) {
            Owner user1 = new Owner("Alice", "Smith", "alice@example.com", 100.0);
            Owner user2 = new Owner("Bob", "Johnson", "bob@example.com", 50.0);
            Owner user3 = new Owner("Carl", "Williams", "carl@example.com", 80.0);
            Owner user4 = new Owner("Diana", "Brown", "diana@example.com", 60.0);
            Owner user5 = new Owner("Eve", "Davis", "eve@example.com", 120.0);
            ownerRepository.saveAll(List.of(user1, user2, user3, user4, user5));

            List<Pet> pets = new ArrayList<>();

            pets.add(createCat("Whiskers", "Fluffy and lazy", LocalDate.of(2020, 6, 1)));
            pets.add(createCat("Misty", "Energetic hunter", LocalDate.of(2021, 3, 10)));
            pets.add(createCat("Luna", "Playful and curious", LocalDate.of(2019, 8, 23)));
            pets.add(createCat("Cleo", "Elegant and shy", LocalDate.of(2018, 12, 1)));
            pets.add(createCat("Shadow", "Loves to nap", LocalDate.of(2022, 4, 5)));

            pets.add(createDog("Rex", "Strong and loyal", LocalDate.of(2018, 1, 1), 4));
            pets.add(createDog("Max", "Champion show dog", LocalDate.of(2019, 5, 20), 7));
            pets.add(createDog("Bruno", "Gentle giant", LocalDate.of(2022, 1, 1), 2));
            pets.add(createDog("Rocky", "Energetic runner", LocalDate.of(2020, 9, 12), 5));
            pets.add(createDog("Buddy", "Loves kids", LocalDate.of(2021, 6, 30), 3));

            petRepository.saveAll(pets);
        }
    }

    private Cat createCat(String name, String description, LocalDate dob) {
        Cat cat = new Cat();
        cat.setName(name);
        cat.setDescription(description);
        cat.setDateOfBirth(dob);
        cat.setPrice(calculateCatPrice(dob));
        return cat;
    }

    private Dog createDog(String name, String description, LocalDate dob, int rating) {
        Dog dog = new Dog();
        dog.setName(name);
        dog.setDescription(description);
        dog.setDateOfBirth(dob);
        dog.setRating(rating);
        dog.setPrice(calculateDogPrice(dob, rating));
        return dog;
    }

    private double calculateCatPrice(LocalDate dob) {
        long age = ChronoUnit.YEARS.between(dob, LocalDate.now());
        return age;
    }

    private double calculateDogPrice(LocalDate dob, int rating) {
        long age = ChronoUnit.YEARS.between(dob, LocalDate.now());
        return age + rating;
    }
}
