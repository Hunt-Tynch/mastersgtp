package com.mastersgtp.mastersgtp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mastersgtp.mastersgtp.entity.Judge;

public interface JudgeRepository extends JpaRepository<Judge, Integer> {

    Judge findByName(String name);

}
