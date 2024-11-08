package com.mastersgtp.mastersgtp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mastersgtp.mastersgtp.entity.Dog;
import com.mastersgtp.mastersgtp.entity.Scratch;
import com.mastersgtp.mastersgtp.repository.DogRepository;
import com.mastersgtp.mastersgtp.repository.JudgeRepository;
import com.mastersgtp.mastersgtp.repository.ScratchRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScratchService {

    private ScratchRepository scratchRepository;
    private JudgeRepository judgeRepository;
    private DogRepository dogRepository;

    public String newScratch(Scratch scratch) {
        if (!dogRepository.existsById(scratch.getDog().getNumber()))
            return "Dog does not exist.";
        if (!judgeRepository.existsById(scratch.getJudge().getNumber()))
            return "Judge does not exist.";
        Scratch s = scratchRepository.findByDog(scratch.getDog());
        if (s != null)
            return "Dog already scratched.";
        Dog savedDog = dogRepository.findById(scratch.getDog().getNumber()).get();
        savedDog.setScratched(true);
        dogRepository.save(savedDog);
        scratchRepository.save(scratch);
        return "Scratch Created.";
    }

    public void deleteScratch(Long id) {
        if (!scratchRepository.existsById(id))
            return;
        Scratch s = scratchRepository.findById(id).get();
        Dog savedDog = dogRepository.findById(s.getDog().getNumber()).get();
        savedDog.setScratched(false);
        dogRepository.save(savedDog);
        scratchRepository.deleteById(id);
    }

    public List<Scratch> getScratch() {
        return scratchRepository.findAll();
    }
}
