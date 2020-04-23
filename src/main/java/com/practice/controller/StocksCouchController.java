package com.practice.controller;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.model.StockCouch;
import com.practice.model.StockCouchEvent;
import com.practice.repositories.StocksCouchbaseRepository;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/couch/stocks")
public class StocksCouchController {
	
	@Autowired
	StocksCouchbaseRepository couchRepo;

	@GetMapping("/all")
	public Flux<StockCouch> getAll() {
		return couchRepo.findAll();
	}

	@GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<StockCouch> getEvents() {
		return couchRepo.findAll();

	}

	@GetMapping(value = "/{profession}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<StockCouchEvent> getEventsByProfession(@PathVariable("profession")
    final String profession) {
		return couchRepo.findByProfession(profession).flatMap(stock -> {

			Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
			Flux<StockCouchEvent> stockEventFlux =
                    Flux.fromStream(
                            Stream.generate(() -> new StockCouchEvent(stock,
                                    new Date()))
                    );

			return Flux.zip(interval, stockEventFlux).map(Tuple2::getT2);

		});

	}
}
