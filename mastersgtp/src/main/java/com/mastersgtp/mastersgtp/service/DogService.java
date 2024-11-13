package com.mastersgtp.mastersgtp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mastersgtp.mastersgtp.entity.Dog;
import com.mastersgtp.mastersgtp.entity.StakeType;
import com.mastersgtp.mastersgtp.repository.DogRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DogService {

    private DogRepository dogRepository;

    public Dog newDog(Dog dog) {
        if (dogRepository.existsById(dog.getNumber()))
            return null;
        Dog d = new Dog(dog.getNumber(), dog.getName(), dog.getStake(), dog.getRegNumber(), dog.getSire(), dog.getDam(),
                dog.getOwner(), dog.getOwnerTown(), dog.getOwnerState());
        return dogRepository.save(d);
    }

    public List<Dog> addDogs(List<Dog> dogs) {
        ArrayList<Dog> failedDogs = new ArrayList<Dog>();
        for (Dog d : dogs) {
            if (newDog(d) == null)
                failedDogs.add(d);
        }
        return failedDogs;
    }

    public void editDog(Dog dog) {
        if (!dogRepository.existsById(dog.getNumber()))
            return;
        Dog d = dogRepository.findById(dog.getNumber()).get();
        d.setName(dog.getName());
        d.setStake(dog.getStake());
        d.setRegNumber(dog.getRegNumber());
        d.setOwner(dog.getOwner());
        d.setOwnerState(dog.getOwnerState());
        d.setOwnerTown(dog.getOwnerTown());
        d.setDam(dog.getDam());
        d.setSire(dog.getSire());
        dogRepository.save(dog);
    }

    public Dog getDog(int number) {
        if (!dogRepository.existsById(number))
            return null;
        return dogRepository.findById(number).get();
    }

    public List<Dog> getDogs() {
        return dogRepository.findAll();
    }

    public List<Dog> getDogsByTotal() {
        return dogRepository.findAllByOrderByTotalDesc();
    }

    public List<Dog> getDogByTotalAndStake(StakeType stake) {
        return dogRepository.findByStakeOrderByTotalDesc(stake);
    }

    public List<Dog> getDogsByTotalAndOwner(String owner) {
        return dogRepository.findByOwnerOrderByTotalDesc(owner);
    }

    public List<Dog> getDogsByOwner(String owner) {
        return dogRepository.findByOwner(owner);
    }

    public void deleteDog(int number) {
        dogRepository.deleteById(number);
    }

}
