package com.practice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.model.Stock;
import com.practice.repositories.StocksMongoRepository;

@Service
public class Consumer {
	
	@Autowired
	StocksMongoRepository mongoRepo;
		
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
		System.out.println("Consumed Stock message: " + stock.toString());
	}
	
}
