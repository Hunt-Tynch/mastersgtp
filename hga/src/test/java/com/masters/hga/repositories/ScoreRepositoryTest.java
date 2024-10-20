package com.masters.hga.repositories;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.masters.hga.entity.Dog;
import com.masters.hga.entity.Score;
import com.masters.hga.entity.enums.StakeType;

@ActiveProfiles("test")
@SpringBootTest
public class ScoreRepositoryTest {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private DogRepository dogRepository;

    @BeforeEach
    void setUp() {
        scoreRepository.deleteAll();
        dogRepository.deleteAll();
    }

    @Test
    void testFindByDogNumber() {
        // Creating Dog instances and saving them
        Dog dog1 = new Dog(null, 1L, StakeType.ALL_AGE, "Dog 1", null, null, null, null);
        Dog dog2 = new Dog(null, 2L, StakeType.ALL_AGE, "Dog 2", null, null, null, null);
        Dog dog3 = new Dog(null, 3L, StakeType.ALL_AGE, "Dog 3", null, null, null, null);
        Dog dog4 = new Dog(null, 4L, StakeType.ALL_AGE, "Dog 4", null, null, null, null);
        Dog dog5 = new Dog(null, 5L, StakeType.ALL_AGE, "Dog 5", null, null, null, null);

        dog1 = dogRepository.save(dog1);
        dog2 = dogRepository.save(dog2);
        dog3 = dogRepository.save(dog3);
        dog4 = dogRepository.save(dog4);
        dog5 = dogRepository.save(dog5);

        // Create and save the first score with dog1
        Score score1 = new Score("10:00", dog1);
        Score savedScore1 = scoreRepository.save(score1);

        Score score2 = new Score("10:30", dog1, dog2, dog3);
        Score savedScore2 = scoreRepository.save(score2);

        Score score3 = new Score("11:00", dog5, dog4, dog3);
        Score savedScore3 = scoreRepository.save(score3);

        // Assert results
        assertAll("Score Contents",
                () -> assertEquals(scoreRepository.findAllScoresByDogNumber(1L).get(0).getId(), savedScore1.getId()),
                () -> assertEquals(scoreRepository.findAllScoresByDogNumber(1L).get(1).getId(), savedScore2.getId()),
                () -> assertEquals(scoreRepository.findAllScoresByDogNumber(2L).get(0).getId(), savedScore2.getId()),
                () -> assertEquals(scoreRepository.findAllScoresByDogNumber(3L).get(0).getId(), savedScore2.getId()),
                () -> assertEquals(scoreRepository.findAllScoresByDogNumber(3L).get(1).getId(), savedScore3.getId()),
                () -> assertEquals(scoreRepository.findAllScoresByDogNumber(4L).get(0).getId(), savedScore3.getId()),
                () -> assertEquals(scoreRepository.findAllScoresByDogNumber(5L).get(0).getId(), savedScore3.getId()));
    }

}
