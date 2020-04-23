package com.practice.repositories;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.practice.model.Stock;

import reactor.core.publisher.Flux;

public interface StocksMongoRepository extends ReactiveMongoRepository<Stock, String> {

	@Query("{ 'profession': ?0 }")
	Flux<Stock> findByProfession(String profession);
	
}
