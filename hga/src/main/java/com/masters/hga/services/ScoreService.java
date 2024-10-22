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

        // Fetch managed Dog entities by their ID (number)
        Dog firstDog = dogRepository.findById(score.getFirstDog().getNumber()).get();
        firstDog.setSdscore(firstDog.getSdscore() + 35); // Update score

        Dog secondDog = score.getSecondDog() != null
                ? dogRepository.findById(score.getSecondDog().getNumber()).get()
                : null;
        if (secondDog != null) {
            secondDog.setSdscore(secondDog.getSdscore() + 30); // Update score
        }

        Dog thirdDog = score.getThirdDog() != null
                ? dogRepository.findById(score.getThirdDog().getNumber()).get()
                : null;
        if (thirdDog != null) {
            thirdDog.setSdscore(thirdDog.getSdscore() + 25); // Update score
        }

        Dog fourthDog = score.getFourthDog() != null
                ? dogRepository.findById(score.getFourthDog().getNumber()).get()
                : null;
        if (fourthDog != null) {
            fourthDog.setSdscore(fourthDog.getSdscore() + 20); // Update score
        }

        Dog fifthDog = score.getFifthDog() != null
                ? dogRepository.findById(score.getFifthDog().getNumber()).get()
                : null;
        if (fifthDog != null) {
            fifthDog.setSdscore(fifthDog.getSdscore() + 15); // Update score
        }

        // Set updated dogs back to score entity
        score.setFirstDog(firstDog);
        score.setSecondDog(secondDog);
        score.setThirdDog(thirdDog);
        score.setFourthDog(fourthDog);
        score.setFifthDog(fifthDog);

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
        Score score = scoreRepository.findById(id).get();
        Dog firstDog = dogRepository.findById(score.getFirstDog().getNumber()).get();
        firstDog.setSdscore(firstDog.getSdscore() - 35);

        Dog secondDog = score.getSecondDog() != null
                ? dogRepository.findById(score.getSecondDog().getNumber()).get()
                : null;
        if (secondDog != null) {
            secondDog.setSdscore(secondDog.getSdscore() - 30); // Update score
        }

        Dog thirdDog = score.getThirdDog() != null
                ? dogRepository.findById(score.getThirdDog().getNumber()).get()
                : null;
        if (thirdDog != null) {
            thirdDog.setSdscore(thirdDog.getSdscore() - 25); // Update score
        }

        Dog fourthDog = score.getFourthDog() != null
                ? dogRepository.findById(score.getFourthDog().getNumber()).get()
                : null;
        if (fourthDog != null) {
            fourthDog.setSdscore(fourthDog.getSdscore() - 20); // Update score
        }

        Dog fifthDog = score.getFifthDog() != null
                ? dogRepository.findById(score.getFifthDog().getNumber()).get()
                : null;
        if (fifthDog != null) {
            fifthDog.setSdscore(fifthDog.getSdscore() - 15); // Update score
        }

        // Set updated dogs back to score entity
        score.setFirstDog(firstDog);
        score.setSecondDog(secondDog);
        score.setThirdDog(thirdDog);
        score.setFourthDog(fourthDog);
        score.setFifthDog(fifthDog);
        scoreRepository.save(score);
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
    public ScoreDTO updateScore(ScoreDTO dto) {
        Score score = scoreRepository.findById(dto.getId()).get();
        Dog firstDog = dogRepository.findById(score.getFirstDog().getNumber()).get();
        firstDog.setSdscore(firstDog.getSdscore() - 35);

        Dog secondDog = score.getSecondDog() != null
                ? dogRepository.findById(score.getSecondDog().getNumber()).get()
                : null;
        if (secondDog != null) {
            secondDog.setSdscore(secondDog.getSdscore() - 30); // Update score
        }

        Dog thirdDog = score.getThirdDog() != null
                ? dogRepository.findById(score.getThirdDog().getNumber()).get()
                : null;
        if (thirdDog != null) {
            thirdDog.setSdscore(thirdDog.getSdscore() - 25); // Update score
        }

        Dog fourthDog = score.getFourthDog() != null
                ? dogRepository.findById(score.getFourthDog().getNumber()).get()
                : null;
        if (fourthDog != null) {
            fourthDog.setSdscore(fourthDog.getSdscore() - 20); // Update score
        }

        Dog fifthDog = score.getFifthDog() != null
                ? dogRepository.findById(score.getFifthDog().getNumber()).get()
                : null;
        if (fifthDog != null) {
            fifthDog.setSdscore(fifthDog.getSdscore() - 15); // Update score
        }
        score.setFirstDog(firstDog);
        score.setSecondDog(secondDog);
        score.setThirdDog(thirdDog);
        score.setFourthDog(fourthDog);
        score.setFifthDog(fifthDog);
        scoreRepository.save(score);

        Score newScore = ScoreMapper.INSTANCE.toEntity(dto);

        firstDog = dogRepository.findById(newScore.getFirstDog().getNumber()).get();
        firstDog.setSdscore(firstDog.getSdscore() + 35); // Update score

        secondDog = newScore.getSecondDog() != null
                ? dogRepository.findById(newScore.getSecondDog().getNumber()).get()
                : null;
        if (secondDog != null) {
            secondDog.setSdscore(secondDog.getSdscore() + 30); // Update score
        }

        thirdDog = newScore.getThirdDog() != null
                ? dogRepository.findById(newScore.getThirdDog().getNumber()).get()
                : null;
        if (thirdDog != null) {
            thirdDog.setSdscore(thirdDog.getSdscore() + 25); // Update score
        }

        fourthDog = newScore.getFourthDog() != null
                ? dogRepository.findById(newScore.getFourthDog().getNumber()).get()
                : null;
        if (fourthDog != null) {
            fourthDog.setSdscore(fourthDog.getSdscore() + 20); // Update score
        }

        fifthDog = newScore.getFifthDog() != null
                ? dogRepository.findById(newScore.getFifthDog().getNumber()).get()
                : null;
        if (fifthDog != null) {
            fifthDog.setSdscore(fifthDog.getSdscore() + 15); // Update score
        }

        // Set updated dogs back to newScore entity
        newScore.setFirstDog(firstDog);
        newScore.setSecondDog(secondDog);
        newScore.setThirdDog(thirdDog);
        newScore.setFourthDog(fourthDog);
        newScore.setFifthDog(fifthDog);

        // Save the newScore, which will also persist the updated dog entities due to
        // cascade settings
        Score savedScore = scoreRepository.save(newScore);

        // Return the saved Score as a DTO
        return ScoreMapper.INSTANCE.toDto(savedScore);
    }

}
