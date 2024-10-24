package com.masters.hga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.masters.hga.entity.Judge;
import com.masters.hga.entity.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("SELECT s FROM Score s WHERE s.firstDog.number = :dogNumber OR " +
            "s.secondDog.number = :dogNumber OR " +
            "s.thirdDog.number = :dogNumber OR " +
            "s.fourthDog.number = :dogNumber OR " +
            "s.fifthDog.number = :dogNumber")
    List<Score> findAllScoresByDogNumber(@Param("dogNumber") Long dogNumber);

    List<Score> findByJudge(Judge judge);

    List<Score> findAllScoreByDay(int day);

    @Query("SELECT s FROM Score s where s.day = :day AND (s.firstDog.number = :dogNumber OR " +
            "s.secondDog.number = :dogNumber OR " +
            "s.thirdDog.number = :dogNumber OR " +
            "s.fourthDog.number = :dogNumber OR " +
            "s.fifthDog.number = :dogNumber)")
    List<Score> findAllScoreByDayAndDogNumber(@Param("day") int day, Long dogNumber);

}
