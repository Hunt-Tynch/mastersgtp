package com.mastersgtp.mastersgtp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mastersgtp.mastersgtp.entity.Dog;
import com.mastersgtp.mastersgtp.entity.Judge;
import com.mastersgtp.mastersgtp.entity.Scratch;

public interface ScratchRepository extends JpaRepository<Scratch, Long> {

    List<Scratch> findByJudge(Judge judge);

    Scratch findByDog(Dog dog);

}
