package com.mastersgtp.mastersgtp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mastersgtp.mastersgtp.entity.Dog;
import com.mastersgtp.mastersgtp.entity.StakeType;

public interface DogRepository extends JpaRepository<Dog, Integer> {

    boolean existsByNumberAndScratched(int number, boolean scratched);

    List<Dog> findAllByOrderByTotalDesc();

    List<Dog> findByOwner(String owner);

    List<Dog> findByOwnerOrderByTotalDesc(String owner);

    List<Dog> findByStakeOrderByTotalDesc(StakeType stake);

}
