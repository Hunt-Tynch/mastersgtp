package com.masters.hga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.hga.entity.Dog;
import com.masters.hga.entity.Kennel;
import com.masters.hga.entity.enums.StakeType;

public interface DogRepository extends JpaRepository<Dog, Long> {

    List<Dog> findByStakeOrderByTotalDesc(StakeType stake);

    List<Dog> findByKennelOrderByTotalDesc(Kennel kennel);

}
