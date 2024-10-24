package com.masters.hga.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.masters.hga.dto.DogDTO;
import com.masters.hga.dto.ScoreDTO;
import com.masters.hga.entity.Dog;
import com.masters.hga.entity.Judge;
import com.masters.hga.entity.enums.StakeType;
import com.masters.hga.mapper.DogMapper;
import com.masters.hga.mapper.JudgeMapper;
import com.masters.hga.repositories.DogRepository;
import com.masters.hga.repositories.JudgeRepository;
import com.masters.hga.repositories.ScoreRepository;

@SpringBootTest
@ActiveProfiles("test")
public class DogServiceTest {

    @Autowired
    private DogService dogService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private JudgeRepository judgeRepository;

    @Autowired
    private DogRepository dogRepository;

    @BeforeEach
    void setUp() {
        scoreRepository.deleteAll();
        judgeRepository.deleteAll();
        dogRepository.deleteAll();
    }

    @Test
    void testCreateDog() {
        DogDTO dto1 = new DogDTO(null, 1l, StakeType.ALL_AGE, "dog1", "number", "sire", "dam", null);
        DogDTO dto2 = new DogDTO(null, 2l, StakeType.ALL_AGE, "dog2", "number", "sire", "dam", null);
        DogDTO dto3 = new DogDTO(null, 3l, StakeType.ALL_AGE, "dog3", "number", "sire", "dam", null);
        DogDTO dto4 = new DogDTO(null, 4l, StakeType.ALL_AGE, "dog4", "number", "sire", "dam", null);
        DogDTO dto5 = new DogDTO(null, 5l, StakeType.ALL_AGE, "dog5", "number", "sire", "dam", null);

        Dog dog1 = DogMapper.INSTANCE.toEntity(dogService.createDog(dto1));
        Dog dog2 = DogMapper.INSTANCE.toEntity(dogService.createDog(dto2));
        Dog dog3 = DogMapper.INSTANCE.toEntity(dogService.createDog(dto3));
        Dog dog4 = DogMapper.INSTANCE.toEntity(dogService.createDog(dto4));
        Dog dog5 = DogMapper.INSTANCE.toEntity(dogService.createDog(dto5));

        assertAll("Dog Contents", () -> assertTrue(dogRepository.existsById(dog1.getNumber())),
                () -> assertTrue(dogRepository.existsById(dog2.getNumber())),
                () -> assertTrue(dogRepository.existsById(dog3.getNumber())),
                () -> assertTrue(dogRepository.existsById(dog4.getNumber())),
                () -> assertTrue(dogRepository.existsById(dog5.getNumber())));

        Judge judge1 = judgeRepository.save(new Judge(1L, "pin", "judge1"));

        ScoreDTO scoreDTO = new ScoreDTO(600, JudgeMapper.INSTANCE.toDto(judge1), 1,
                DogMapper.INSTANCE.toDTO(dog2), DogMapper.INSTANCE.toDTO(dog3), DogMapper.INSTANCE.toDTO(dog1),
                DogMapper.INSTANCE.toDTO(dog4), DogMapper.INSTANCE.toDTO(dog5));

        scoreService.createScore(scoreDTO);

        List<Dog> dogsByTotal = DogMapper.INSTANCE.toEntityList(dogService.getAllDogsByScore());

        assertAll("Dog Scores", () -> assertEquals(35, dogsByTotal.get(0).getTotal()),
                () -> assertEquals(30, dogsByTotal.get(1).getTotal()),
                () -> assertEquals(25, dogsByTotal.get(2).getTotal()),
                () -> assertEquals(20, dogsByTotal.get(3).getTotal()),
                () -> assertEquals(15, dogsByTotal.get(4).getTotal()));
    }

}
