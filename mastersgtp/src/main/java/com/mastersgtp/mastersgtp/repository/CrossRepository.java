package com.mastersgtp.mastersgtp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mastersgtp.mastersgtp.entity.Cross;
import com.mastersgtp.mastersgtp.entity.Dog;
import com.mastersgtp.mastersgtp.entity.Judge;

public interface CrossRepository extends JpaRepository<Cross, Long> {

    List<Cross> findByDogsContainsOrderByCrossTimeDesc(Dog dog);

    List<Cross> findByJudgeOrderByCrossTimeDesc(Judge judge);

    List<Cross> findByDayOrderByCrossTimeDesc(int day);

    List<Cross> findByDayAndDogsContainsAndCrossTimeBetween(int day, Dog dog, int start, int end);

}
