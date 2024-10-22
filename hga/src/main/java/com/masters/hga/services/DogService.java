package com.masters.hga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masters.hga.dto.DogDTO;
import com.masters.hga.entity.Dog;
import com.masters.hga.entity.Kennel;
import com.masters.hga.entity.enums.StakeType;
import com.masters.hga.mapper.DogMapper;
import com.masters.hga.repositories.DogRepository;
import com.masters.hga.repositories.KennelRepository;

@Service
public class DogService {

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private KennelRepository kennelRepository;

    public DogDTO createDog(DogDTO dogDto) {
        Dog dog = DogMapper.INSTANCE.toEntity(dogDto);
        Dog savedDog = dogRepository.save(dog);
        return DogMapper.INSTANCE.toDTO(savedDog);
    }

    public List<DogDTO> getAllDogs() {
        return DogMapper.INSTANCE.toDtoList(dogRepository.findAll());
    }

    public List<DogDTO> getAllDogsByScore() {
        return DogMapper.INSTANCE.toDtoList(dogRepository.findAllDogs());
    }

    public List<DogDTO> getDogsByStake(StakeType stake) {
        List<Dog> dogs = dogRepository.findByStake(stake);
        return DogMapper.INSTANCE.toDtoList(dogs);
    }

    public List<DogDTO> getDogsByStakeOrder(StakeType stake) {
        return DogMapper.INSTANCE.toDtoList(dogRepository.findByStakeOrderByTotalDesc(stake));
    }

    public List<DogDTO> getDogsByKennel(Long kennelId) {
        Kennel kennel = kennelRepository.findById(kennelId).get();
        return DogMapper.INSTANCE.toDtoList(dogRepository.findByKennel(kennel));
    }

    public List<DogDTO> getDogsByKennelOrder(Long kennelId) {
        return DogMapper.INSTANCE.toDtoList(dogRepository.findByKennelOrderByTotalDesc(kennelId));
    }

    public boolean kennelExists(Long id) {
        return kennelRepository.existsById(id);
    }

    public boolean dogExists(Long number) {
        return dogRepository.existsById(number);
    }

    public void deleteDog(Long number) {
        Dog dog = DogMapper.INSTANCE.toEntity(getDogByNumber(number));
        dogRepository.delete(dog);
    }

    public DogDTO getDogByNumber(Long number) {
        Dog dog = dogRepository.findById(number).get();
        return DogMapper.INSTANCE.toDTO(dog);
    }

    public DogDTO updateEScore(DogDTO dto) {
        Dog dog = DogMapper.INSTANCE.toEntity(getDogByNumber(dto.getNumber()));
        dog.setEscore(dto.getEscore());
        Dog savedDog = dogRepository.save(dog);
        return DogMapper.INSTANCE.toDTO(savedDog);
    }

    public DogDTO updateTotalScore(DogDTO dto) {
        Dog dog = DogMapper.INSTANCE.toEntity(getDogByNumber(dto.getNumber()));
        dog.setTotal(dto.getTotal());
        Dog savedDog = dogRepository.save(dog);
        return DogMapper.INSTANCE.toDTO(savedDog);
    }

}
