package com.masters.hga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.masters.hga.entity.Dog;
import com.masters.hga.entity.Kennel;
import com.masters.hga.entity.enums.StakeType;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {

    @Query("SELECT d FROM Dog d WHERE d.stake = ?1 ORDER BY (sdscore + escore) DESC")
    List<Dog> findByStakeOrderByTotalDesc(StakeType stake);

    @Query("SELECT d FROM Dog d WHERE d.kennel.id = ?1 ORDER BY (sdscore + escore) DESC")
    List<Dog> findByKennelOrderByTotalDesc(Long kennelId);

    @Query("SELECT d FROM Dog d ORDER BY (sdscore + escore) DESC")
    List<Dog> findAllDogs();

    List<Dog> findByStake(StakeType stake);

    List<Dog> findByKennel(Kennel kennel);

}
