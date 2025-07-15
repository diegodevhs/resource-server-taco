package com.dhsdev.taco_cloud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.dhsdev.taco_cloud.models.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long>{
}
