package com.masters.hga.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.masters.hga.entity.Hunt;
import com.masters.hga.mapper.HuntMapper;
import com.masters.hga.repositories.DogRepository;
import com.masters.hga.repositories.HuntRepository;
import com.masters.hga.repositories.JudgeRepository;
import com.masters.hga.repositories.KennelRepository;
import com.masters.hga.repositories.ScoreRepository;
import com.masters.hga.repositories.ScratchRepository;

@SpringBootTest
@ActiveProfiles("test")
public class HuntServiceTest {

    @Autowired
    private HuntService huntService;

    @Autowired
    private HuntRepository huntRepository;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private KennelRepository kennelRepository;

    @Autowired
    private JudgeRepository judgeRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ScratchRepository scratchRepository;

    @BeforeEach
    public void setUp() {
        huntRepository.deleteAll();
        dogRepository.deleteAll();
        kennelRepository.deleteAll();
        judgeRepository.deleteAll();
        scoreRepository.deleteAll();
        scratchRepository.deleteAll();
    }

    @Test
    void testNewHunt() {

        Hunt hunt = new Hunt("Test Hunt", "10-15-2024", 10);
        Hunt savedHunt = HuntMapper.INSTANCE.toEntity(huntService.newHunt(HuntMapper.INSTANCE.toDto(hunt)));
        assertAll("Hunt Contents",
                () -> assertTrue(savedHunt.getId() > 0),
                () -> assertEquals("Test Hunt", savedHunt.getName()),
                () -> assertEquals("10-15-2024", savedHunt.getDate()),
                () -> assertEquals(10, savedHunt.getInterval()));
    }

    @Test
    void testUpdateHunt() {

    }
}
