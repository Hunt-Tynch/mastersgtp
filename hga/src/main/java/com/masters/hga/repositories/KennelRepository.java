package com.masters.hga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.hga.entity.Kennel;

public interface KennelRepository extends JpaRepository<Kennel, Long> {

}
