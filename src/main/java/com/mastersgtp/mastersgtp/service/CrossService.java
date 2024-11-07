package com.mastersgtp.mastersgtp.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mastersgtp.mastersgtp.entity.Cross;
import com.mastersgtp.mastersgtp.entity.Dog;
import com.mastersgtp.mastersgtp.entity.Hunt;
import com.mastersgtp.mastersgtp.repository.CrossRepository;
import com.mastersgtp.mastersgtp.repository.DogRepository;
import com.mastersgtp.mastersgtp.repository.HuntRepository;
import com.mastersgtp.mastersgtp.repository.JudgeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CrossService {

    private CrossRepository crossRepository;
    private JudgeRepository judgeRepository;
    private DogRepository dogRepository;
    private HuntRepository huntRepository;

    public List<Cross> findByJudge(int number) {
        if (!judgeRepository.existsById(number)) {
            return List.of();
        }
        return crossRepository.findByJudgeOrderByCrossTimeDesc(judgeRepository.findById(number).get());
    }

    public List<Cross> findByDog(int number) {
        if (!dogRepository.existsById(number)) {
            return List.of();
        }
        return crossRepository.findByDogsContainsOrderByCrossTimeDesc(dogRepository.findById(number).get());
    }

    public String newCross(Cross cross) {
        if (!judgeRepository.existsById(cross.getJudge().getNumber())) {
            return "Judge: " + cross.getJudge().getNumber() + " does not exist.";
        }

        // Validate all dogs in a single call
        List<Dog> dogs = cross.getDogs();
        Set<Integer> dogIds = dogs.stream().map(Dog::getNumber).collect(Collectors.toSet());
        List<Dog> existingDogs = dogRepository.findAllById(dogIds);
        if (existingDogs.size() != dogIds.size()) {
            for (Dog d : dogs) {
                if (!existingDogs.contains(d))
                    return "Dog: " + d.getNumber() + " does not exist.";
            }
        }

        Hunt hunt = huntRepository.findAll().get(0);
        if (hunt.getTimeInterval() == 0) {
            return manageCrossNoInterval(cross, dogs);
        }

        int startInterval = calculateStartInterval(hunt, cross);
        int points = 35;
        Map<Integer, Dog> dogMap = existingDogs.stream().collect(Collectors.toMap(Dog::getNumber, d -> d));

        for (Dog dog : dogs) {
            Dog savedDog = dogMap.get(dog.getNumber());
            List<Cross> existingCrosses = crossRepository.findByDayAndDogsContainsAndCrossTimeBetween(
                    cross.getDay(), savedDog, startInterval, startInterval + hunt.getTimeInterval() - 1);

            points = updateDogPlace(points, cross, dog, savedDog, existingCrosses);
        }
        dogRepository.saveAll(dogMap.values());
        crossRepository.save(cross);
        return "Cross successful.";
    }

    private int calculateStartInterval(Hunt hunt, Cross cross) {
        int startTime = hunt.getStartTimes()[cross.getDay()];
        int intervalsPassed = (cross.getCrossTime() - startTime) / hunt.getTimeInterval();
        return (intervalsPassed * hunt.getTimeInterval()) + startTime;
    }

    private int updateDogPlace(int points, Cross cross, Dog dog, Dog savedDog, List<Cross> existingCrosses) {
        int idx = 7;
        if (existingCrosses.size() != 0) {
            for (Cross c : existingCrosses) {
                int index = -1;
                List<Dog> dogs = c.getDogs();
                for (int i = 0; i < dogs.size(); i++) {
                    if (dogs.get(i).getNumber() == dog.getNumber()) {
                        index = i;
                        break;
                    }
                }
                if (index < idx) {
                    idx = index;
                    if (index == 0)
                        break;
                }
            }
            if (idx > cross.getDogs().indexOf(dog)) {
                savedDog.deletePlace(cross.getDay(), (35 - (idx * 5)));
                savedDog.place(cross.getDay(), points);
            }
        } else {
            savedDog.place(cross.getDay(), points);
        }
        return points - 5;
    }

    private String manageCrossNoInterval(Cross cross, List<Dog> dogs) {
        int points = 35;
        for (Dog dog : dogs) {
            dog.place(cross.getDay(), points);
            points -= 5;
        }
        crossRepository.save(cross);
        return "Cross successful.";
    }

    private String manageDeleteCrossNoInterval(Cross cross, List<Dog> dogs) {
        int points = 35;
        for (Dog dog : dogs) {
            dog.deletePlace(cross.getDay(), points);
            points -= 5;
        }
        crossRepository.save(cross);
        return "Cross successful.";
    }

    public void deleteCross(Long id) {
        if (!crossRepository.existsById(id))
            return;

        Cross cross = crossRepository.findById(id).orElse(null);
        if (cross == null)
            return;

        Hunt hunt = huntRepository.findAll().get(0);
        if (hunt.getTimeInterval() == 0) {
            manageDeleteCrossNoInterval(cross, cross.getDogs());
        } else {
            removeCrossWithInterval(cross, hunt);
        }
        dogRepository.saveAll(cross.getDogs());
        crossRepository.delete(cross);
    }

    private void removeCrossWithInterval(Cross cross, Hunt hunt) {
        int interval = hunt.getTimeInterval();
        int startInterval = calculateStartInterval(hunt, cross);
        int points = 35;

        for (Dog dog : cross.getDogs()) {
            List<Cross> existingCrosses = crossRepository.findByDayAndDogsContainsAndCrossTimeBetween(
                    cross.getDay(), dog, startInterval, startInterval + interval - 1);

            int idx = 7;
            for (Cross c : existingCrosses) {
                int index = c.getDogs().indexOf(dog);
                if (c.getId() != cross.getId() && index < idx) {
                    idx = index;
                    if (index == 0)
                        break;
                }
            }

            if (idx != 7 && idx > cross.getDogs().indexOf(dog)) {
                dog.deletePlace(cross.getDay(), points);
                dog.place(cross.getDay(), (35 - (idx * 5)));
            }
            points -= 5;
        }
    }

    public void editCross(Long id, Cross cross) {
        if (!crossRepository.existsById(id))
            return;
        deleteCross(id);
        newCross(cross);
    }

    public List<Cross> getAllCrossesForDay(int day) {
        return crossRepository.findByDayOrderByCrossTimeDesc(day);
    }
}
