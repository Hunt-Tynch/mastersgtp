package com.mastersgtp.mastersgtp.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
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
        return crossRepository.findByDogInMapOrderByCrossTimeDesc(dogRepository.findById(number).get());
    }

    public String newCross(Cross cross) {
        if (!judgeRepository.existsById(cross.getJudge().getNumber())) {
            return "Judge: " + cross.getJudge().getNumber() + " does not exist.";
        }

        // Validate and populate dogs with actual entities
        Map<Dog, Integer> dogs = cross.getDogs();
        Set<Integer> dogIds = dogs.keySet().stream().map(Dog::getNumber).collect(Collectors.toSet());
        List<Dog> existingDogs = dogRepository.findAllById(dogIds);
        Map<Integer, Dog> existingDogMap = existingDogs.stream()
                .collect(Collectors.toMap(Dog::getNumber, Function.identity()));

        // Ensure all provided Dog numbers exist in the database
        if (existingDogMap.size() != dogIds.size()) {
            for (Dog d : dogs.keySet()) {
                if (!existingDogMap.containsKey(d.getNumber())) {
                    return "Dog: " + d.getNumber() + " does not exist.";
                }
            }
        }

        // Replace the Dog keys with the actual Dog entities from the database
        Map<Dog, Integer> updatedDogs = dogs.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> existingDogMap.get(entry.getKey().getNumber()),
                        Map.Entry::getValue));

        // Set the updated map back to the Cross object
        cross.setDogs(updatedDogs);
        crossRepository.save(cross);

        Hunt hunt = huntRepository.findAll().get(0);
        if (hunt.getTimeInterval() == 0) {
            return manageCrossNoInterval(cross, cross.getDogs());
        }

        int startInterval = calculateStartInterval(hunt, cross);

        for (Map.Entry<Dog, Integer> entry : updatedDogs.entrySet()) {
            Dog savedDog = entry.getKey();
            int pointsEarned = entry.getValue();

            List<Cross> existingCrosses = crossRepository.findByDayAndDogInMapAndCrossTimeBetween(
                    cross.getDay(), savedDog, startInterval, startInterval + hunt.getTimeInterval() - 1);

            updateDogPlace(cross, savedDog, pointsEarned, existingCrosses);
        }

        dogRepository.saveAll(cross.getDogs().keySet());
        crossRepository.save(cross);
        return "Cross successful.";
    }

    private int calculateStartInterval(Hunt hunt, Cross cross) {
        int startTime = hunt.getStartTimes()[cross.getDay()];
        int intervalsPassed = (cross.getCrossTime() - startTime) / hunt.getTimeInterval();
        return (intervalsPassed * hunt.getTimeInterval()) + startTime;
    }

    private void updateDogPlace(Cross cross, Dog savedDog, int pointsEarned, List<Cross> existingCrosses) {
        Cross highestPointsCross = null;
        int highestPoints = 0;

        // Find the existing cross with the highest points for the same dog
        for (Cross existingCross : existingCrosses) {
            int existingPoints = existingCross.getDogs().get(savedDog);
            if (existingPoints > highestPoints && existingCross.getId() != cross.getId()) {
                highestPoints = existingPoints;
                highestPointsCross = existingCross;
            }
        }

        // Compare pointsEarned with highest points in existingCrosses
        if (pointsEarned > highestPoints) {
            if (highestPointsCross != null) {
                savedDog.deletePlace(cross.getDay(), highestPoints); // Remove old points
                savedDog.getDupScores().add(highestPointsCross.getId()); // Add old cross ID to duplicates
            }
            savedDog.place(cross.getDay(), pointsEarned); // Apply new points
            savedDog.getDupScores().remove(cross.getId()); // Ensure current cross ID is not in duplicates
        } else {
            // If this cross's points are lower, add its ID to the duplicate list
            savedDog.getDupScores().add(cross.getId());
        }
    }

    private String manageCrossNoInterval(Cross cross, Map<Dog, Integer> dogs) {
        for (Map.Entry<Dog, Integer> entry : dogs.entrySet()) {
            Dog dog = entry.getKey();
            int pointsEarned = entry.getValue();
            dog.place(cross.getDay(), pointsEarned);
        }
        crossRepository.save(cross);
        return "Cross successful.";
    }

    private String manageDeleteCrossNoInterval(Cross cross, Map<Dog, Integer> dogs) {
        Set<Map.Entry<Dog, Integer>> entries = dogs.entrySet();
        for (Map.Entry<Dog, Integer> entry : entries) {
            Dog dog = entry.getKey();
            int pointsEarned = entry.getValue();
            dog.deletePlace(cross.getDay(), pointsEarned);
        }
        dogRepository.saveAll(dogs.keySet());
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
        crossRepository.delete(cross);
    }

    private void removeCrossWithInterval(Cross cross, Hunt hunt) {
        int interval = hunt.getTimeInterval();
        int startInterval = calculateStartInterval(hunt, cross);

        for (Map.Entry<Dog, Integer> entry : cross.getDogs().entrySet()) {
            Dog dog = entry.getKey();
            int pointsEarned = entry.getValue();

            List<Cross> existingCrosses = crossRepository.findByDayAndDogInMapAndCrossTimeBetween(
                    cross.getDay(), dog, startInterval, startInterval + interval - 1);

            Cross highestPointsCross = null;
            int highestPoints = 0;

            for (Cross existingCross : existingCrosses) {
                int existingPoints = existingCross.getDogs().get(dog);
                if (existingPoints > highestPoints && existingCross.getId() != cross.getId()) {
                    highestPoints = existingPoints;
                    highestPointsCross = existingCross;
                }
            }

            if (highestPointsCross != null && pointsEarned >= highestPoints) {
                dog.place(cross.getDay(), highestPoints);
                dog.getDupScores().remove(highestPointsCross.getId()); // Remove ID from duplicates
            }
            dog.deletePlace(cross.getDay(), pointsEarned);
            dog.getDupScores().remove(cross.getId());
        }
        dogRepository.saveAll(cross.getDogs().keySet());
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

    public List<Cross> getDogAndDayCross(int number, int day) {
        return crossRepository.findByDogInMapAndDayOrderByCrossTime(dogRepository.findById(number).get(), day);
    }
}
