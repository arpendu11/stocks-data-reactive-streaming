package com.practice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.model.Stock;
import com.practice.model.StockCouch;
import com.practice.repositories.StocksCouchbaseRepository;
import com.practice.repositories.StocksMongoRepository;

@Service
public class Consumer {
	
	@Autowired
	StocksMongoRepository mongoRepo;
	
	@Autowired
	StocksCouchbaseRepository couchRepo;
		
	@KafkaListener(topics = "stocks_profiles_joined",
			groupId = "group_json",
			containerFactory = "kafkaListenerContainerFactory")
	public void consumeStockJson(String message) throws JsonMappingException, JsonProcessingException {
		System.out.println("Consumed Stock message: " + message);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode json = mapper.readTree(message);
		Stock stock = new Stock();
		stock.setDate(json.findValue("date").textValue());
		stock.setCompany(json.findValue("company").textValue());
		stock.setOpen(json.findValue("open").floatValue());
		stock.setClose(json.findValue("close").floatValue());
		stock.setHigh(json.findValue("high").floatValue());
		stock.setLow(json.findValue("low").floatValue());
		stock.setVolume(json.findValue("volume").intValue());
		stock.setProfession(json.findValue("profession").textValue());
		stock.setSector(json.findValue("sector").textValue());
		stock.setAddress(json.findValue("address").textValue());
		stock.setRegistration(json.findValue("registration").textValue());
		mongoRepo.save(stock).subscribe();
		System.out.println("Consumed Stock message for Mongo: " + stock.toString());
		StockCouch stockCouch = new StockCouch();
		stockCouch.setDate(json.findValue("date").textValue());
		stockCouch.setCompany(json.findValue("company").textValue());
		stockCouch.setOpen(json.findValue("open").floatValue());
		stockCouch.setClose(json.findValue("close").floatValue());
		stockCouch.setHigh(json.findValue("high").floatValue());
		stockCouch.setLow(json.findValue("low").floatValue());
		stockCouch.setVolume(json.findValue("volume").intValue());
		stockCouch.setProfession(json.findValue("profession").textValue());
		stockCouch.setSector(json.findValue("sector").textValue());
		stockCouch.setAddress(json.findValue("address").textValue());
		stockCouch.setRegistration(json.findValue("registration").textValue());
		couchRepo.save(stockCouch).subscribe();
		System.out.println("Consumed Stock message for Couchbase: " + stockCouch.toString());
	}
	
}
