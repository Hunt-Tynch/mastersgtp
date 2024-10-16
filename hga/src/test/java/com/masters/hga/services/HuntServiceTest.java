package com.masters.hga.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.masters.hga.entity.Dog;
import com.masters.hga.entity.Hunt;
import com.masters.hga.entity.Judge;
import com.masters.hga.entity.Kennel;
import com.masters.hga.entity.Score;
import com.masters.hga.entity.Scratch;
import com.masters.hga.entity.enums.ScratchType;
import com.masters.hga.entity.enums.StakeType;
import com.masters.hga.mapper.HuntMapper;
import com.masters.hga.mapper.KennelMapper;
import com.masters.hga.repositories.DogRepository;
import com.masters.hga.repositories.HuntRepository;
import com.masters.hga.repositories.JudgeRepository;
import com.masters.hga.repositories.KennelRepository;
import com.masters.hga.repositories.ScoreRepository;
import com.masters.hga.repositories.ScratchRepository;

@ActiveProfiles("test")
@SpringBootTest
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
		judgeRepository.deleteAll();
		scoreRepository.deleteAll();
		scratchRepository.deleteAll();
		dogRepository.deleteAll();
		huntRepository.deleteAll();
		kennelRepository.deleteAll();
	}

	@Test
	void testNewHunt() {

		final Hunt hunt = new Hunt("Test Hunt", "10-15-2024", 10);
		final Hunt savedHunt = HuntMapper.INSTANCE.toEntity(huntService.newHunt(HuntMapper.INSTANCE.toDto(hunt)));
		assertAll("Hunt Contents",
				() -> assertTrue(savedHunt.getId() > 0),
				() -> assertEquals("Test Hunt", savedHunt.getName()),
				() -> assertEquals("10-15-2024", savedHunt.getDate()),
				() -> assertEquals(10, savedHunt.getTimeInterval()));
		
		Kennel kennel = new Kennel("Owner", "Town", "State");
		final Kennel savedKennel = kennelRepository.save(kennel);
		
		Dog dog = new Dog(savedHunt, 1L, StakeType.ALL_AGE, "Dog", "regNumer", "sire", "dam",  savedKennel);
		final Dog savedDog = dogRepository.save(dog);
		
		Judge judge = new Judge(1L, "PIN", "Judge Name");
		final Judge savedJudge = judgeRepository.save(judge);
		
		ArrayList<Dog> list = new ArrayList<Dog>();
		list.add(savedDog);
		Score score = new Score("time", list);
		Score savedScore = scoreRepository.save(score);
		
		Scratch scratch = new Scratch(savedDog, ScratchType.BABBLING, "time");
		Scratch savedScratch = scratchRepository.save(scratch);
		
		assertAll("Table contents", () -> assertTrue(kennelRepository.existsById(savedKennel.getId())), 
				() -> assertTrue(dogRepository.existsById(savedDog.getNumber())),
				() -> assertTrue(judgeRepository.existsById(savedJudge.getId())),
				() -> assertTrue(scoreRepository.existsById(savedScore.getId())),
				() -> assertTrue(scratchRepository.existsById(savedScratch.getId())));
		
		assertAll("New Hunt Contents", () -> assertTrue(huntService.newHunt(HuntMapper.INSTANCE.toDto(hunt)).getId() > 0), 
				() -> assertFalse(kennelRepository.count() > 0), 
				() -> assertFalse(dogRepository.count() > 0),
				() -> assertFalse(judgeRepository.count() > 0),
				() -> assertFalse(scoreRepository.count() > 0),
				() -> assertFalse(scratchRepository.count() > 0));
		
	}

	@Test
	void testUpdateHunt() {

	}
}
