package com.mastersgtp.mastersgtp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mastersgtp.mastersgtp.entity.Dog;
import com.mastersgtp.mastersgtp.entity.StakeType;
import com.mastersgtp.mastersgtp.service.DogService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/dog")
public class DogController {

    @Autowired
    private DogService dogService;

    @PostMapping
    public Dog createDog(@RequestBody Dog dog) {
        return dogService.newDog(dog);
    }

    @PostMapping("/batch")
    public List<Dog> addDogs(@RequestBody List<Dog> dogs) {
        return dogService.addDogs(dogs);
    }

    @PutMapping
    public void editDog(@RequestBody Dog dog) {
        dogService.editDog(dog);
    }

    @GetMapping("/{number}")
    public Dog getDog(@PathVariable int number) {
        return dogService.getDog(number);
    }

    @GetMapping
    public List<Dog> getAllDogs() {
        return dogService.getDogs();
    }

    @GetMapping("/by-total")
    public List<Dog> getDogsByTotal() {
        return dogService.getDogsByTotal();
    }

    @GetMapping("/by-total-and-stake/{stake}")
    public List<Dog> getDogsByTotalAndStake(@PathVariable StakeType stake) {
        return dogService.getDogByTotalAndStake(stake);
    }

    @GetMapping("/by-owner/{owner}")
    public List<Dog> getDogsByOwner(@PathVariable String owner) {
        return dogService.getDogsByOwner(owner);
    }

    @GetMapping("/by-owner-and-total/{owner}")
    public List<Dog> getDogsByOwnerAndTotal(@PathVariable String owner) {
        return dogService.getDogsByTotalAndOwner(owner);
    }

    @DeleteMapping("/{number}")
    public void deleteDog(@PathVariable int number) {
        dogService.deleteDog(number);
    }
}
