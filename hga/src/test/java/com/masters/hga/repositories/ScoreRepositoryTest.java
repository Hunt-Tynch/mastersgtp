package com.masters.hga.repositories;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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
        List<Dog> dogs1 = List.of(dog1);
        Score score1 = new Score("10:00", dogs1);
        Score savedScore1 = scoreRepository.save(score1);

        // Create and save the second score with dog1, dog2, and dog3
        List<Dog> dogs2 = List.of(dog1, dog2, dog3); // Using the same dog1
        Score score2 = new Score("10:30", dogs2);
        Score savedScore2 = scoreRepository.save(score2); // This should be valid now

        // Create and save the third score with dog3, dog4, and dog5
        List<Dog> dogs3 = List.of(dog3, dog4, dog5);
        Score score3 = new Score("11:00", dogs3);
        Score savedScore3 = scoreRepository.save(score3);

        // Assert results
        assertAll("Score Contents",
                () -> assertEquals(scoreRepository.findByDogsNumber(1L).get(0).getId(), savedScore1.getId()),
                () -> assertEquals(scoreRepository.findByDogsNumber(1L).get(1).getId(), savedScore2.getId()),
                () -> assertEquals(scoreRepository.findByDogsNumber(2L).get(0).getId(), savedScore2.getId()),
                () -> assertEquals(scoreRepository.findByDogsNumber(3L).get(0).getId(), savedScore2.getId()),
                () -> assertEquals(scoreRepository.findByDogsNumber(3L).get(1).getId(), savedScore3.getId()),
                () -> assertEquals(scoreRepository.findByDogsNumber(4L).get(0).getId(), savedScore3.getId()),
                () -> assertEquals(scoreRepository.findByDogsNumber(5L).get(0).getId(), savedScore3.getId()));
    }

}
