package com.masters.hga.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.masters.hga.dto.ScoreDTO;
import com.masters.hga.entity.Dog;
import com.masters.hga.entity.Judge;
import com.masters.hga.entity.Score;
import com.masters.hga.entity.enums.StakeType;
import com.masters.hga.mapper.DogMapper;
import com.masters.hga.mapper.JudgeMapper;
import com.masters.hga.mapper.ScoreMapper;
import com.masters.hga.repositories.DogRepository;
import com.masters.hga.repositories.JudgeRepository;
import com.masters.hga.repositories.ScoreRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
public class ScoreServiceTest {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private JudgeRepository judgeRepository;

    @BeforeEach
    void setUp() {
        scoreRepository.deleteAll();
        dogRepository.deleteAll();
    }

    @Test
    @Transactional
    void testCreateScore() {
        Dog dog1 = dogRepository.save(new Dog(null, 1L, StakeType.ALL_AGE, "dog1", "", "", "", null));
        Dog dog2 = dogRepository.save(new Dog(null, 2L, StakeType.ALL_AGE, "dog2", "", "", "", null));
        Dog dog3 = dogRepository.save(new Dog(null, 3L, StakeType.ALL_AGE, "dog3", "", "", "", null));
        Dog dog4 = dogRepository.save(new Dog(null, 4L, StakeType.ALL_AGE, "dog4", "", "", "", null));
        Dog dog5 = dogRepository.save(new Dog(null, 5L, StakeType.ALL_AGE, "dog5", "", "", "", null));

        Judge judge1 = judgeRepository.save(new Judge(1L, "pin", "judge1"));
        Judge judge2 = judgeRepository.save(new Judge(2L, "pin", "judge2"));

        ScoreDTO scoreDTO = new ScoreDTO("10:00", JudgeMapper.INSTANCE.toDto(judge1),
                DogMapper.INSTANCE.toDTO(dog1), DogMapper.INSTANCE.toDTO(dog2), DogMapper.INSTANCE.toDTO(dog3),
                DogMapper.INSTANCE.toDTO(dog4), DogMapper.INSTANCE.toDTO(dog5));

        // Create score using the service
        ScoreDTO createdScoreDTO = scoreService.createScore(scoreDTO);
        Score createdScore = ScoreMapper.INSTANCE.toEntity(createdScoreDTO);

        // Assertions
        assertAll("Score Contents",
                () -> assertTrue(createdScore.getId() > 0),
                () -> assertEquals(35, createdScore.getFirstDog().getSdscore()),
                () -> assertEquals(createdScore.getFirstDog().getSdscore(),
                        dogRepository.findById(dog1.getNumber()).orElseThrow().getSdscore()),
                () -> assertEquals(30, dogRepository.findById(dog2.getNumber()).get().getSdscore()),
                () -> assertEquals(25, dogRepository.findById(dog3.getNumber()).get().getSdscore()),
                () -> assertEquals(20, dogRepository.findById(dog4.getNumber()).get().getSdscore()),
                () -> assertEquals(15, dogRepository.findById(dog5.getNumber()).get().getSdscore()));

        ScoreDTO scoreDTO2 = new ScoreDTO("10:00", JudgeMapper.INSTANCE.toDto(judge2),
                DogMapper.INSTANCE.toDTO(dog5), DogMapper.INSTANCE.toDTO(dog2), DogMapper.INSTANCE.toDTO(dog1),
                DogMapper.INSTANCE.toDTO(dog3), DogMapper.INSTANCE.toDTO(dog4));

        // Create score using the service
        ScoreDTO createdScoreDTO2 = scoreService.createScore(scoreDTO2);
        Score createdScore2 = ScoreMapper.INSTANCE.toEntity(createdScoreDTO2);

        assertAll("Score Contents",
                () -> assertTrue(createdScore2.getId() > 0),
                () -> assertEquals(60, dogRepository.findById(dog1.getNumber()).get().getSdscore()),
                () -> assertEquals(60, dogRepository.findById(dog2.getNumber()).get().getSdscore()),
                () -> assertEquals(45, dogRepository.findById(dog3.getNumber()).get().getSdscore()),
                () -> assertEquals(35, dogRepository.findById(dog4.getNumber()).get().getSdscore()),
                () -> assertEquals(50, dogRepository.findById(dog5.getNumber()).get().getSdscore()));
    }

    @Test
    void testDeleteScore() {
        Dog dog1 = dogRepository.save(new Dog(null, 1L, StakeType.ALL_AGE, "dog1", "", "", "", null));
        Dog dog2 = dogRepository.save(new Dog(null, 2L, StakeType.ALL_AGE, "dog2", "", "", "", null));
        Dog dog3 = dogRepository.save(new Dog(null, 3L, StakeType.ALL_AGE, "dog3", "", "", "", null));
        Dog dog4 = dogRepository.save(new Dog(null, 4L, StakeType.ALL_AGE, "dog4", "", "", "", null));
        Dog dog5 = dogRepository.save(new Dog(null, 5L, StakeType.ALL_AGE, "dog5", "", "", "", null));

        Judge judge1 = judgeRepository.save(new Judge(1L, "pin", "judge1"));

        ScoreDTO scoreDTO = new ScoreDTO("10:00", JudgeMapper.INSTANCE.toDto(judge1),
                DogMapper.INSTANCE.toDTO(dog1), DogMapper.INSTANCE.toDTO(dog2), DogMapper.INSTANCE.toDTO(dog3),
                DogMapper.INSTANCE.toDTO(dog4), DogMapper.INSTANCE.toDTO(dog5));

        // Create score using the service
        ScoreDTO createdScoreDTO = scoreService.createScore(scoreDTO);
        Score createdScore = ScoreMapper.INSTANCE.toEntity(createdScoreDTO);

        scoreService.deleteScore(createdScore.getId());

        assertAll("Score Contents", () -> assertTrue(scoreRepository.count() == 0),
                () -> assertEquals(0, dogRepository.findById(dog1.getNumber()).get().getSdscore()),
                () -> assertEquals(0, dogRepository.findById(dog2.getNumber()).get().getSdscore()),
                () -> assertEquals(0, dogRepository.findById(dog3.getNumber()).get().getSdscore()),
                () -> assertEquals(0, dogRepository.findById(dog4.getNumber()).get().getSdscore()),
                () -> assertEquals(0, dogRepository.findById(dog5.getNumber()).get().getSdscore()));
    }
}
