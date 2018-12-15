package com.cenfotec.examen.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cenfotec.examen.entities.Paquete;

public interface PaqueteRepository extends MongoRepository<Paquete, Long> {
	@Query(value="{'courier._id': ?0}")
	public Optional<Paquete> findOneByCourierId (Long courierId);
	
	@Query(value="{'prealerta': ?0}")
	public List<Paquete> findByStatus(boolean status);
	
	@Query(value="{'cuenta._id': ?0}")
	public Paquete findByCuenta(Long cuentaId);
}