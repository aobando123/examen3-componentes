package com.cenfotec.examen.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cenfotec.examen.entities.Paquete;

public interface PaqueteRepository extends MongoRepository<Paquete, Long> {
@Query(value="{'courier._id': ?0}")
 public Optional<Paquete> findOneByCourierId (Long courierId);
}
