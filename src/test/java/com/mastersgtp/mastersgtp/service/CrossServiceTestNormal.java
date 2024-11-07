package com.mastersgtp.mastersgtp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mastersgtp.mastersgtp.entity.Cross;
import com.mastersgtp.mastersgtp.entity.Dog;
import com.mastersgtp.mastersgtp.entity.Hunt;
import com.mastersgtp.mastersgtp.entity.Judge;
import com.mastersgtp.mastersgtp.repository.CrossRepository;
import com.mastersgtp.mastersgtp.repository.DogRepository;
import com.mastersgtp.mastersgtp.repository.HuntRepository;
import com.mastersgtp.mastersgtp.repository.JudgeRepository;

@SpringBootTest
public class CrossServiceTestNormal {

    @Autowired
    private CrossService crossService;

    @Autowired
    private CrossRepository crossRepository;

    @Autowired
    private JudgeRepository judgeRepository;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private HuntRepository huntRepository;

    private Judge judge;
    private Dog dog1, dog2;
    private Hunt hunt;
    private Cross cross;

    @BeforeEach
    public void setup() {
        crossRepository.deleteAll();
        judgeRepository.deleteAll();
        dogRepository.deleteAll();
        huntRepository.deleteAll();
        // Initialize Judge, Dogs, Hunt, and Cross objects
        judge = new Judge(1, "Judge Judy", "12345");
        judge = judgeRepository.save(judge);

        dog1 = new Dog(101, "Buddy", null, "REG101", "Sire1", "Dam1", "Owner1", "Town1", "State1");
        dog2 = new Dog(102, "Max", null, "REG102", "Sire2", "Dam2", "Owner2", "Town2", "State2");
        dog1 = dogRepository.save(dog1);
        dog2 = dogRepository.save(dog2);

        hunt = new Hunt("Spring Hunt", "2024-04-10", 10, new int[] { 100, 100, 100, 100 });
        huntRepository.save(hunt);
        cross = new Cross(judge, Arrays.asList(dog1, dog2), 1, 120);
    }

    @Test
    void testNewCross() {
        Cross existingCross = new Cross(judge, Arrays.asList(dog2, dog1), 1, 121); // Initial cross where dog2 is ranked
        crossService.newCross(existingCross);

        String result = crossService.newCross(cross);
        // Assert - Ensure "Cross successful." is returned
        assertEquals("Cross successful.", result);

        // Verify scores - dog2 should retain its highest score from the initial cross
        assertEquals(35, dogRepository.findById(dog2.getNumber()).get().getSdscore(1),
                "Dog2 should retain 35 points as its highest score within the interval.");
        assertEquals(35, dogRepository.findById(dog1.getNumber()).get().getSdscore(1),
                "Dog1 should receive 35 points for being ranked first in the new cross.");

        Cross newCross = new Cross(judge, Arrays.asList(dog1, dog2), 1, 119);
        String result1 = crossService.newCross(newCross);
        // Assert - Ensure "Cross successful." is returned
        assertEquals("Cross successful.", result1);
        assertEquals(65, dogRepository.findById(dog2.getNumber()).get().getSdscore(1));
        assertEquals(70, dogRepository.findById(dog1.getNumber()).get().getSdscore(1));

        Cross newCross1 = new Cross(judge, Arrays.asList(dog1, dog2), 0, 119);
        String result2 = crossService.newCross(newCross1);
        // Assert - Ensure "Cross successful." is returned
        assertEquals("Cross successful.", result2);
        assertEquals(30, dogRepository.findById(dog2.getNumber()).get().getSdscore(0));
        assertEquals(35, dogRepository.findById(dog1.getNumber()).get().getSdscore(0));
        assertEquals(108, dogRepository.findById(dog2.getNumber()).get().getTotal());
        assertEquals(119, dogRepository.findById(dog1.getNumber()).get().getTotal());
    }
}
