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

import com.masters.hga.dto.KennelDTO;
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

        kennelService.deleteKennel(savedKennel.getId());
        assertTrue(kennelRepository.count() == 0);
    }

    @Test
    void getAllKennels() {
        assertTrue(kennelService.getAllKennels().isEmpty());

        KennelDTO dto1 = new KennelDTO("Owner1", "Town1", "State1");
        KennelDTO dto2 = new KennelDTO("Owner2", "Town2", "State2");
        KennelDTO dto3 = new KennelDTO("Owner3", "Town3", "State3");

        KennelDTO savedDto1 = kennelService.createKennel(dto1);
        KennelDTO savedDto2 = kennelService.createKennel(dto2);
        KennelDTO savedDto3 = kennelService.createKennel(dto3);

        List<KennelDTO> allKennels = kennelService.getAllKennels();

        assertAll("Kennel Content", () -> assertEquals(savedDto1.getId(), allKennels.get(0).getId()),
                () -> assertEquals(savedDto2.getId(), allKennels.get(1).getId()),
                () -> assertEquals(savedDto3.getId(), allKennels.get(2).getId()));

    }
}
