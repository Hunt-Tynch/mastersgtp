package com.mastersgtp.mastersgtp.service;

import org.springframework.stereotype.Service;

import com.mastersgtp.mastersgtp.entity.Hunt;
import com.mastersgtp.mastersgtp.repository.CrossRepository;
import com.mastersgtp.mastersgtp.repository.DogRepository;
import com.mastersgtp.mastersgtp.repository.HuntRepository;
import com.mastersgtp.mastersgtp.repository.JudgeRepository;
import com.mastersgtp.mastersgtp.repository.ScratchRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HuntService {

    private HuntRepository huntRepository;
    private DogRepository dogRepository;
    private JudgeRepository judgeRepository;
    private CrossRepository crossRepository;
    private ScratchRepository scratchRepository;

    public Hunt newHunt(Hunt hunt) {
        scratchRepository.deleteAll();
        crossRepository.deleteAll();
        judgeRepository.deleteAll();
        dogRepository.deleteAll();
        huntRepository.deleteAll();
        return huntRepository.save(hunt);
    }

    public void setStartTime(int day, int startTime) {
        Hunt hunt = huntRepository.findAll().get(0);
        if (hunt == null)
            return;
        hunt.setStartTime(day, startTime);
        huntRepository.save(hunt);
    }

    public Hunt editHunt(Hunt hunt) {
        Hunt savedHunt = huntRepository.findAll().get(0);
        if (savedHunt == null)
            return null;
        savedHunt.setName(hunt.getName());
        savedHunt.setDate(hunt.getDate());
        savedHunt.setTimeInterval(hunt.getTimeInterval());
        return huntRepository.save(savedHunt);
    }

    public Hunt getHunt() {
        if (huntRepository.count() == 0)
            return null;
        return huntRepository.findAll().get(0);
    }

}