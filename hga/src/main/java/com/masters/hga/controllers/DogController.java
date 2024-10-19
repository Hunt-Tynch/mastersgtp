package com.masters.hga.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masters.hga.dto.DogDTO;
import com.masters.hga.entity.enums.StakeType;
import com.masters.hga.services.DogService;

@RestController
@CrossOrigin("*")
@RequestMapping("/dogs")
public class DogController {

    @Autowired
    private DogService dogService;

    @GetMapping("/kennel/{id}")
    public List<DogDTO> getByKennelId(@PathVariable Long id) {
        return dogService.getDogsByKennel(id);
    }

    @GetMapping("{number}")
    public DogDTO getDog(@PathVariable Long number) {
        return dogService.getDogByNumber(number);
    }

    @GetMapping("/stake/{stake}")
    public List<DogDTO> getDogsByStake(@PathVariable StakeType stake) {
        return dogService.getDogsByStake(stake);
    }

    @GetMapping("/stake")
    public List<DogDTO> getAllDogs() {
        return dogService.getAllDogs();
    }

    @PostMapping
    public List<DogDTO> postDogs(@RequestBody List<DogDTO> dtos) {
        return dogService.createDogs(dtos);
    }

    @DeleteMapping("{number}")
    public void deleteDog(@PathVariable Long number) {
        dogService.deleteDog(number);
    }

}
