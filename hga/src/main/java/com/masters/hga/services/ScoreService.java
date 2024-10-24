package com.masters.hga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masters.hga.dto.ScoreDTO;
import com.masters.hga.entity.Dog;
import com.masters.hga.entity.Score;
import com.masters.hga.mapper.ScoreMapper;
import com.masters.hga.repositories.DogRepository;
import com.masters.hga.repositories.JudgeRepository;
import com.masters.hga.repositories.ScoreRepository;

import jakarta.transaction.Transactional;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private JudgeRepository judgeRepository;

    @Transactional
    public ScoreDTO createScore(ScoreDTO dto) {
        // Convert DTO to Entity
        Score score = ScoreMapper.INSTANCE.toEntity(dto);
        int day = score.getDay();

        // Fetch managed Dog entities by their ID (number)
        Dog firstDog = dogRepository.findById(score.getFirstDog().getNumber()).get();
        firstDog.place(35, day);

        Dog secondDog = score.getSecondDog() != null
                ? dogRepository.findById(score.getSecondDog().getNumber()).get()
                : null;
        if (secondDog != null) {
            secondDog.place(30, day); // Update score
        }

        Dog thirdDog = score.getThirdDog() != null
                ? dogRepository.findById(score.getThirdDog().getNumber()).get()
                : null;
        if (thirdDog != null) {
            thirdDog.place(25, day); // Update score
        }

        Dog fourthDog = score.getFourthDog() != null
                ? dogRepository.findById(score.getFourthDog().getNumber()).get()
                : null;
        if (fourthDog != null) {
            fourthDog.place(20, day); // Update score
        }

        Dog fifthDog = score.getFifthDog() != null
                ? dogRepository.findById(score.getFifthDog().getNumber()).get()
                : null;
        if (fifthDog != null) {
            fifthDog.place(15, day); // Update score
        }

        Dog sixthDog = score.getSixthDog() != null
                ? dogRepository.findById(score.getSixthDog().getNumber()).get()
                : null;
        if (sixthDog != null) {
            sixthDog.place(10, day); // Update score
        }

        Dog seventhDog = score.getSeventhDog() != null
                ? dogRepository.findById(score.getSeventhDog().getNumber()).get()
                : null;
        if (seventhDog != null) {
            seventhDog.place(5, day); // Update score
        }

        // Set updated dogs back to score entity
        score.setFirstDog(firstDog);
        score.setSecondDog(secondDog);
        score.setThirdDog(thirdDog);
        score.setFourthDog(fourthDog);
        score.setFifthDog(fifthDog);
        score.setSixthDog(sixthDog);
        score.setSeventhDog(seventhDog);

        // Save the score, which will also persist the updated dog entities due to
        // cascade settings
        Score savedScore = scoreRepository.save(score);

        // Return the saved Score as a DTO
        return ScoreMapper.INSTANCE.toDto(savedScore);
    }

    public boolean scoreExists(Long id) {
        return scoreRepository.existsById(id);
    }

    public boolean dogExists(Long number) {
        return dogRepository.existsById(number);
    }

    public boolean judgeExists(Long id) {
        return judgeRepository.existsById(id);
    }

    public boolean validScore(ScoreDTO dto) {
        if (!judgeExists(dto.getJudge().getId())) {
            System.out.println("1");
            return false;
        }
        if (!dogExists(dto.getFirstDog().getNumber())) {
            System.out.println("2");
            return false;
        }
        if (dto.getSecondDog() != null && !dogExists(dto.getSecondDog().getNumber())) {
            System.out.println("3");
            return false;
        }
        if (dto.getThirdDog() != null && !dogExists(dto.getThirdDog().getNumber())) {
            System.out.println("4");
            return false;
        }
        if (dto.getFourthDog() != null && !dogExists(dto.getFourthDog().getNumber())) {
            System.out.println("5");
            return false;
        }
        if (dto.getFifthDog() != null && !dogExists(dto.getFifthDog().getNumber())) {
            System.out.println("6");
            return false;
        }

        return true;
    }

    @Transactional
    public void deleteScore(Long id) {
        undoScore(id);
        scoreRepository.deleteById(id);
    }

    public List<ScoreDTO> getAllScores() {
        return ScoreMapper.INSTANCE.toDtoList(scoreRepository.findAll());
    }

    public List<ScoreDTO> getAllScoresByJudge(Long id) {
        return ScoreMapper.INSTANCE.toDtoList(scoreRepository.findByJudge(judgeRepository.findById(id).get()));
    }

    public List<ScoreDTO> getAllScoresByDog(Long number) {
        return ScoreMapper.INSTANCE.toDtoList(scoreRepository.findAllScoresByDogNumber(number));
    }

    @Transactional
    private void undoScore(Long id) {
        Score score = scoreRepository.findById(id).get();
        int day = score.getDay();

        // Fetch managed Dog entities by their ID (number)
        Dog firstDog = dogRepository.findById(score.getFirstDog().getNumber()).get();
        firstDog.removePlace(35, day);

        Dog secondDog = score.getSecondDog() != null
                ? dogRepository.findById(score.getSecondDog().getNumber()).get()
                : null;
        if (secondDog != null) {
            secondDog.removePlace(30, day); // Update score
        }

        Dog thirdDog = score.getThirdDog() != null
                ? dogRepository.findById(score.getThirdDog().getNumber()).get()
                : null;
        if (thirdDog != null) {
            thirdDog.removePlace(25, day); // Update score
        }

        Dog fourthDog = score.getFourthDog() != null
                ? dogRepository.findById(score.getFourthDog().getNumber()).get()
                : null;
        if (fourthDog != null) {
            fourthDog.removePlace(20, day); // Update score
        }

        Dog fifthDog = score.getFifthDog() != null
                ? dogRepository.findById(score.getFifthDog().getNumber()).get()
                : null;
        if (fifthDog != null) {
            fifthDog.removePlace(15, day); // Update score
        }

        Dog sixthDog = score.getSixthDog() != null
                ? dogRepository.findById(score.getSixthDog().getNumber()).get()
                : null;
        if (sixthDog != null) {
            sixthDog.removePlace(10, day); // Update score
        }

        Dog seventhDog = score.getSeventhDog() != null
                ? dogRepository.findById(score.getSeventhDog().getNumber()).get()
                : null;
        if (seventhDog != null) {
            seventhDog.removePlace(5, day); // Update score
        }

        // Set updated dogs back to score entity
        score.setFirstDog(firstDog);
        score.setSecondDog(secondDog);
        score.setThirdDog(thirdDog);
        score.setFourthDog(fourthDog);
        score.setFifthDog(fifthDog);
        score.setSixthDog(sixthDog);
        score.setSeventhDog(seventhDog);
        scoreRepository.save(score);
    }

    @Transactional
    public ScoreDTO updateScore(ScoreDTO dto) {
        undoScore(dto.getId());
        return createScore(dto);
    }

}
