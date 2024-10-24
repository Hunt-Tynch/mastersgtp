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

        @Query("SELECT d FROM Dog d WHERE d.stake = ?1 ORDER BY "
                        + "(sdscore1 + "
                        + "(CASE WHEN sdscore1 >= 60 THEN sdscore1 * 0.1 ELSE 0 END) + "
                        + "sdscore2 + "
                        + "(CASE WHEN sdscore2 >= 60 THEN sdscore2 * 0.2 ELSE 0 END) + "
                        + "sdscore3 + "
                        + "(CASE WHEN sdscore3 >= 60 THEN sdscore3 * 0.3 ELSE 0 END) + "
                        + "sdscore4 + "
                        + "(CASE WHEN sdscore4 >= 60 THEN sdscore4 * 0.4 ELSE 0 END)) DESC")
        List<Dog> findByStakeOrderByTotalDesc(StakeType stake);

        @Query("SELECT d FROM Dog d WHERE d.kennel.id = ?1 ORDER BY "
                        + "(sdscore1 + "
                        + "(CASE WHEN sdscore1 >= 60 THEN sdscore1 * 0.1 ELSE 0 END) + "
                        + "sdscore2 + "
                        + "(CASE WHEN sdscore2 >= 60 THEN sdscore2 * 0.2 ELSE 0 END) + "
                        + "sdscore3 + "
                        + "(CASE WHEN sdscore3 >= 60 THEN sdscore3 * 0.3 ELSE 0 END) + "
                        + "sdscore4 + "
                        + "(CASE WHEN sdscore4 >= 60 THEN sdscore4 * 0.4 ELSE 0 END)) DESC")
        List<Dog> findByKennelOrderByTotalDesc(Long kennelId);

        @Query("SELECT d FROM Dog d ORDER BY "
                        + "(sdscore1 + "
                        + "(CASE WHEN sdscore1 >= 60 THEN sdscore1 * 0.1 ELSE 0 END) + "
                        + "sdscore2 + "
                        + "(CASE WHEN sdscore2 >= 60 THEN sdscore2 * 0.2 ELSE 0 END) + "
                        + "sdscore3 + "
                        + "(CASE WHEN sdscore3 >= 60 THEN sdscore3 * 0.3 ELSE 0 END) + "
                        + "sdscore4 + "
                        + "(CASE WHEN sdscore4 >= 60 THEN sdscore4 * 0.4 ELSE 0 END)) DESC")
        List<Dog> findAllDogs();

        List<Dog> findByStake(StakeType stake);

        List<Dog> findByKennel(Kennel kennel);

}
