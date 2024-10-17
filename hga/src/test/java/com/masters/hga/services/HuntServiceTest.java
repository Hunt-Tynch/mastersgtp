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
import com.masters.hga.repositories.HuntRepository;

@ActiveProfiles("test")
@SpringBootTest
public class HuntServiceTest {

    @Autowired
    private HuntService huntService;

    @Autowired
    private HuntRepository huntRepository;

    @BeforeEach
    public void setUp() {
        huntRepository.deleteAll();
    }

    @Test
    void testNewHunt() {
        Hunt hunt = new Hunt("Test Hunt", "10-15-2024", 10);
        Hunt savedHunt = HuntMapper.INSTANCE.toEntity(huntService.newHunt(HuntMapper.INSTANCE.toDto(hunt)));
        assertAll("Hunt Contents",
                () -> assertTrue(savedHunt.getId() > 0),
                () -> assertEquals("Test Hunt", savedHunt.getName()),
                () -> assertEquals("10-15-2024", savedHunt.getDate()),
                () -> assertEquals(10, savedHunt.getTimeInterval()));
    }

    @Test
    void testUpdateHunt() {
        Hunt hunt = new Hunt("Test Hunt", "10-15-2024", 10);
        Hunt savedHunt = HuntMapper.INSTANCE.toEntity(huntService.newHunt(HuntMapper.INSTANCE.toDto(hunt)));
        assertAll("Hunt Contents",
                () -> assertTrue(savedHunt.getId() > 0),
                () -> assertEquals("Test Hunt", savedHunt.getName()),
                () -> assertEquals("10-15-2024", savedHunt.getDate()),
                () -> assertEquals(10, savedHunt.getTimeInterval()));

        savedHunt.setName("Update Hunt");
        savedHunt.setTimeInterval(5);
        savedHunt.setDate("Today");
        Hunt updatedHunt = HuntMapper.INSTANCE.toEntity(huntService.updateHunt(HuntMapper.INSTANCE.toDto(savedHunt)));

        assertAll("Hunt Contents",
                () -> assertTrue(savedHunt.getId() == updatedHunt.getId()),
                () -> assertEquals("Update Hunt", updatedHunt.getName()),
                () -> assertEquals("Today", updatedHunt.getDate()),
                () -> assertEquals(5, updatedHunt.getTimeInterval()));
    }
}
