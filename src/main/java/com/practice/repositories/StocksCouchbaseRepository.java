package com.practice.repositories;

import java.io.Serializable;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;

import com.practice.model.StockCouch;

import reactor.core.publisher.Flux;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "stockCouch")
public interface StocksCouchbaseRepository extends ReactiveCouchbaseRepository<StockCouch, Serializable> {

	@Query("select META().id AS _ID, META().cas AS _CAS, * from #{#n1ql.bucket} where profession = $1")
	Flux<StockCouch> findByProfession(String profession);
	
}
