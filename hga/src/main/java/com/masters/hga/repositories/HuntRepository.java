package com.masters.hga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.hga.entity.Hunt;

public interface HuntRepository extends JpaRepository<Hunt, Long> {

    Hunt findByName(String name);

}
