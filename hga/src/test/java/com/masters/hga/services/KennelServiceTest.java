package com.masters.hga.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.masters.hga.entity.Kennel;
import com.masters.hga.mapper.KennelMapper;
import com.masters.hga.repositories.KennelRepository;

@ActiveProfiles("test")
@SpringBootTest
public class KennelServiceTest {

    @Autowired
    private KennelService kennelService;

    @Autowired
    private KennelRepository kennelRepository;

    @BeforeEach
    void setUp() {
        kennelRepository.deleteAll();
    }

    @Test
    void testCreateKennel() {
        Kennel kennel = new Kennel("owner", "town", "state");
        Kennel savedKennel = KennelMapper.INSTANCE
                .toEntity(kennelService.createKennel(KennelMapper.INSTANCE.toDto(kennel)));

        assertAll("Kennel Contents", () -> assertTrue(kennelRepository.count() > 0),
                () -> assertEquals("owner", savedKennel.getOwner()),
                () -> assertEquals("town", savedKennel.getTown()),
                () -> assertEquals("state", savedKennel.getState()),
                () -> assertTrue(savedKennel.getId() > 0));

    }

    @Test
    void testDeleteKennel() {
        Kennel kennel = new Kennel("owner", "town", "state");
        Kennel savedKennel = KennelMapper.INSTANCE
                .toEntity(kennelService.createKennel(KennelMapper.INSTANCE.toDto(kennel)));

        assertAll("Kennel Contents", () -> assertTrue(kennelRepository.count() > 0),
                () -> assertEquals("owner", savedKennel.getOwner()),
                () -> assertEquals("town", savedKennel.getTown()),
                () -> assertEquals("state", savedKennel.getState()),
                () -> assertTrue(savedKennel.getId() > 0));

        kennelService.deleteKennel(KennelMapper.INSTANCE.toDto(savedKennel));
        assertTrue(kennelRepository.count() == 0);
    }
}
