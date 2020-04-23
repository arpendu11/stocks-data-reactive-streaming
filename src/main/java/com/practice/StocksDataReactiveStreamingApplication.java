package com.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableMongoAuditing
@EnableReactiveMongoRepositories
@SpringBootApplication
@ComponentScan
@ConfigurationPropertiesScan("com.practice.config")
public class StocksDataReactiveStreamingApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocksDataReactiveStreamingApplication.class, args);
	}

}
