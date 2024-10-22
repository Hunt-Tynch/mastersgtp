package com.masters.hga.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/kennel/{id}/order")
    public List<DogDTO> getByKennelIdOrder(@PathVariable Long id) {
        if (!dogService.kennelExists(id)) {
            return null;
        }
        return dogService.getDogsByKennelOrder(id);
    }

    @GetMapping("{number}")
    public DogDTO getDog(@PathVariable Long number) {
        return dogService.getDogByNumber(number);
    }

    @GetMapping("/stake/{stake}")
    public List<DogDTO> getDogsByStake(@PathVariable StakeType stake) {
        return dogService.getDogsByStake(stake);
    }

    @GetMapping("/stake/{stake}/order")
    public List<DogDTO> getDogsByStakeOrder(@PathVariable StakeType stake) {
        return dogService.getDogsByStakeOrder(stake);
    }

    @GetMapping
    public List<DogDTO> getAllDogs() {
        return dogService.getAllDogs();
    }

    @GetMapping("/score")
    public List<DogDTO> getAllDogsByScore() {
        return dogService.getAllDogsByScore();
    }

    @PostMapping
    public ResponseEntity<List<DogDTO>> postDogs(@RequestBody List<DogDTO> dtos) {
        return createDogs(dtos);
    }

    private ResponseEntity<List<DogDTO>> createDogs(List<DogDTO> dtos) {
        List<DogDTO> newList = new ArrayList<DogDTO>();
        List<DogDTO> oldList = new ArrayList<DogDTO>(dtos);
        for (DogDTO dto : dtos) {
            if (dogService.dogExists(dto.getNumber())) {
                return ResponseEntity.badRequest().body(oldList);
            } else {
                newList.add(dogService.createDog(dto));
                oldList.remove(dto);
            }
        }
        return ResponseEntity.ok(newList);
    }

    @DeleteMapping("{number}")
    public void deleteDog(@PathVariable Long number) {
        dogService.deleteDog(number);
    }

}
