package com.mastersgtp.mastersgtp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mastersgtp.mastersgtp.entity.Cross;
import com.mastersgtp.mastersgtp.entity.Dog;
import com.mastersgtp.mastersgtp.entity.Judge;

public interface CrossRepository extends JpaRepository<Cross, Long> {

    // Query to find Crosses where a specific Dog is part of the dogs map, ordered
    // by Cross Time descending
    @Query("SELECT c FROM Cross c JOIN c.dogs d WHERE key(d) = :dog ORDER BY c.crossTime DESC")
    List<Cross> findByDogInMapOrderByCrossTimeDesc(@Param("dog") Dog dog);

    // Query to find Crosses where a specific Dog is part of the dogs map and on a
    // specific day, ordered by Cross Time
    @Query("SELECT c FROM Cross c JOIN c.dogs d WHERE key(d) = :dog AND c.day = :day ORDER BY c.crossTime")
    List<Cross> findByDogInMapAndDayOrderByCrossTime(@Param("dog") Dog dog, @Param("day") int day);

    // Query to find Crosses where a specific Judge is present, ordered by Cross
    // Time descending
    List<Cross> findByJudgeOrderByCrossTimeDesc(Judge judge);

    // Query to find Crosses on a specific day, ordered by Cross Time descending
    List<Cross> findByDayOrderByCrossTimeDesc(int day);

    // Query to find Crosses within a time range on a specific day where a specific
    // Dog is in the map
    @Query("SELECT c FROM Cross c JOIN c.dogs d WHERE key(d) = :dog AND c.day = :day AND c.crossTime BETWEEN :start AND :end")
    List<Cross> findByDayAndDogInMapAndCrossTimeBetween(@Param("day") int day, @Param("dog") Dog dog,
            @Param("start") int start, @Param("end") int end);

}
