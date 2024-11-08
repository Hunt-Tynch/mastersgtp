package com.mastersgtp.mastersgtp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mastersgtp.mastersgtp.entity.Cross;
import com.mastersgtp.mastersgtp.entity.Dog;
import com.mastersgtp.mastersgtp.entity.Hunt;
import com.mastersgtp.mastersgtp.entity.Judge;
import com.mastersgtp.mastersgtp.repository.CrossRepository;
import com.mastersgtp.mastersgtp.repository.DogRepository;
import com.mastersgtp.mastersgtp.repository.HuntRepository;
import com.mastersgtp.mastersgtp.repository.JudgeRepository;

@SpringBootTest
public class CrossServiceTest {

    @Autowired
    private CrossService crossService;

    @MockBean
    private CrossRepository crossRepository;

    @MockBean
    private JudgeRepository judgeRepository;

    @MockBean
    private DogRepository dogRepository;

    @MockBean
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

        dog1 = new Dog(101, "Buddy", null, "REG101", "Sire1", "Dam1", "Owner1", "Town1", "State1");
        dog2 = new Dog(102, "Max", null, "REG102", "Sire2", "Dam2", "Owner2", "Town2", "State2");

        hunt = new Hunt("Spring Hunt", "2024-04-10", 10, new int[] { 100, 100, 100, 100 });
        cross = new Cross(judge, Arrays.asList(dog1, dog2), 1, 120);

        // Mock repository responses
        when(judgeRepository.existsById(judge.getNumber())).thenReturn(true);
        when(judgeRepository.findById(judge.getNumber())).thenReturn(Optional.of(judge));

        when(dogRepository.findAllById(any())).thenReturn(Arrays.asList(dog1, dog2));
        when(huntRepository.findAll()).thenReturn(Collections.singletonList(hunt));
        when(crossRepository.findByDayAndDogsContainsAndCrossTimeBetween(anyInt(), any(Dog.class), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());

        // Mock saving behavior
        when(crossRepository.save(any(Cross.class))).thenReturn(cross);
    }

    @Test
    public void testNewCrossWithValidJudgeAndDogs() {
        // Act
        String result = crossService.newCross(cross);

        // Assert
        assertEquals("Cross successful.", result);
        verify(judgeRepository, times(1)).existsById(judge.getNumber());
        verify(dogRepository, times(1)).findAllById(any());
        verify(crossRepository, times(1)).save(any(Cross.class));
    }

    @Test
    public void testNewCrossWithNonExistentJudge() {
        // Arrange
        when(judgeRepository.existsById(judge.getNumber())).thenReturn(false);

        // Act
        String result = crossService.newCross(cross);

        // Assert
        assertEquals("Judge: 1 does not exist.", result);
        verify(crossRepository, never()).save(any(Cross.class));
    }

    @Test
    public void testNewCrossWithNonExistentDog() {
        // Arrange
        when(dogRepository.findAllById(any())).thenReturn(Collections.singletonList(dog1));

        // Act
        String result = crossService.newCross(cross);

        // Assert
        assertEquals("Dog: 102 does not exist.", result);
        verify(crossRepository, never()).save(any(Cross.class));
    }

    @Test
    public void testNewCrossWithIntervalCheck() {
        // Arrange
        Cross existingCross = new Cross(judge, Arrays.asList(dog2, dog1), 1, 115); // Initial cross where dog2 is ranked
        crossService.newCross(existingCross);

        cross.setDogs(Arrays.asList(dog1, dog2)); // New cross where dog1 is ranked higher

        // Mock repository to return the existing cross within the same interval
        when(crossRepository.findByDayAndDogsContainsAndCrossTimeBetween(eq(1), eq(dog1), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(existingCross));
        when(crossRepository.findByDayAndDogsContainsAndCrossTimeBetween(eq(1), eq(dog2), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(existingCross));

        // Act - Call newCross with the new cross where dog1 ranks higher
        String result = crossService.newCross(cross);
        // Assert - Ensure "Cross successful." is returned
        assertEquals("Cross successful.", result);

        // Verify scores - dog2 should retain its highest score from the initial cross
        assertEquals(35, dog2.getSdscore(1), "Dog2 should retain 35 points as its highest score within the interval.");
        assertEquals(35, dog1.getSdscore(1), "Dog1 should receive 35 points for being ranked first in the new cross.");

    }

    @Test
    public void testScoreAssignmentBasedOnPlacement() {
        crossService.newCross(cross);

        // Assert - Validate score assignment
        assertEquals(35, dog1.getSdscore(1));
        assertEquals(30, dog2.getSdscore(1));
        verify(crossRepository, times(1)).save(any(Cross.class));
    }

    @Test
    public void testNewCrossWithNoInterval() {
        // Arrange - Set Hunt with no interval (timeInterval = 0)
        hunt.setTimeInterval(0);
        when(huntRepository.findAll()).thenReturn(Collections.singletonList(hunt));

        // Act
        String result = crossService.newCross(cross);

        // Assert
        assertEquals("Cross successful.", result);
        assertEquals(35, dog1.getSdscore(1));
        assertEquals(30, dog2.getSdscore(1));
        verify(crossRepository, times(1)).save(any(Cross.class));
    }
}
