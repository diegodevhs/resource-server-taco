package com.dhsdev.taco_cloud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhsdev.taco_cloud.models.Taco;

public interface TacoRepository extends JpaRepository<Taco, Long>{

}
