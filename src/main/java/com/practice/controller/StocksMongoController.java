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

import com.practice.model.Stock;
import com.practice.model.StockEvent;
import com.practice.repositories.StocksMongoRepository;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/mongo/stocks")
public class StocksMongoController {

	@Autowired
	StocksMongoRepository mongoRepo;

	@GetMapping("/all")
	public Flux<Stock> getAll() {
		return mongoRepo.findAll();
	}

	@GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Stock> getEvents() {
		return mongoRepo.findAll();

	}

	@GetMapping(value = "/{profession}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<StockEvent> getEventsByProfession(@PathVariable("profession")
    final String profession) {
		return mongoRepo.findByProfession(profession).flatMap(stock -> {

			Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
			Flux<StockEvent> stockEventFlux =
                    Flux.fromStream(
                            Stream.generate(() -> new StockEvent(stock,
                                    new Date()))
                    );

			return Flux.zip(interval, stockEventFlux).map(Tuple2::getT2);

		});

	}
}
