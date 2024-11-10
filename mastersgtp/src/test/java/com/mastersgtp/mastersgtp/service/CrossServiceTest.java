package com.mastersgtp.mastersgtp.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.mastersgtp.mastersgtp.entity.Cross;
import com.mastersgtp.mastersgtp.entity.Dog;
import com.mastersgtp.mastersgtp.entity.Hunt;
import com.mastersgtp.mastersgtp.entity.Judge;
import com.mastersgtp.mastersgtp.entity.StakeType;
import com.mastersgtp.mastersgtp.repository.CrossRepository;
import com.mastersgtp.mastersgtp.repository.DogRepository;
import com.mastersgtp.mastersgtp.repository.HuntRepository;
import com.mastersgtp.mastersgtp.repository.JudgeRepository;

@SpringBootTest
@ActiveProfiles("test")
public class CrossServiceTest {

    @Autowired
    private CrossService crossService;

    @Autowired
    private HuntRepository huntRepository;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private JudgeRepository judgeRepository;

    @Autowired
    private CrossRepository crossRepository;

    private Hunt hunt;

    private Judge judge;

    private Dog d1;

    private Dog d2;

    private Dog d3;

    private Dog d4;

    private Dog d5;

    @BeforeEach
    void setUp() throws Exception {
        crossRepository.deleteAll();
        dogRepository.deleteAll();
        judgeRepository.deleteAll();
        huntRepository.deleteAll();
    }

    void start() {
        Hunt hunt = new Hunt("title", "date", 10, new int[] { 300, 300, 300, 300 });
        this.hunt = huntRepository.save(hunt);

        Judge judge = new Judge(1, "Name", "Pin");
        this.judge = judgeRepository.save(judge);

        Dog d1 = new Dog(1, "Dog1", StakeType.DERBY, "", "", "", "", "", "");
        this.d1 = dogRepository.save(d1);

        Dog d2 = new Dog(2, "Dog2", StakeType.DERBY, "", "", "", "", "", "");
        this.d2 = dogRepository.save(d2);

        Dog d3 = new Dog(3, "Dog3", StakeType.DERBY, "", "", "", "", "", "");
        this.d3 = dogRepository.save(d3);

        Dog d4 = new Dog(4, "Dog4", StakeType.DERBY, "", "", "", "", "", "");
        this.d4 = dogRepository.save(d4);

        Dog d5 = new Dog(5, "Dog5", StakeType.DERBY, "", "", "", "", "", "");
        this.d5 = dogRepository.save(d5);
    }

    @Test
    void testDeleteCross() {

    }

    @Test
    void testNewCross() {
        start();

        Map<Dog, Integer> dogMap = new HashMap<>();
        dogMap.put(d1, 35);
        dogMap.put(d2, 30);
        dogMap.put(d3, 25);
        dogMap.put(d4, 20);
        dogMap.put(d5, 15);

        Cross c = new Cross(null, judge, dogMap, 0, 330);
        String message = crossService.newCross(c);
        assertAll("Cross creation", () -> assertEquals("Cross successful.", message),
                () -> assertEquals(35, dogRepository.findById(d1.getNumber()).get().getSdscore(0)));
    }
}
