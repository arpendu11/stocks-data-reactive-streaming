package com.practice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.practice.repositories.StocksMongoRepository;

@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = StocksMongoRepository.class)
public class MongoConfig extends AbstractReactiveMongoConfiguration {

  @Autowired
  private Environment env;

  @Override
  protected String getDatabaseName() {
    return env.getProperty("spring.data.mongodb.database");

  }

  public String createConnectionString() {
	  String mongoPrefix = "mongodb://";
	  String user = env.getProperty("spring.data.mongodb.username");
	  String pwd = env.getProperty("spring.data.mongodb.password");
	  String host = env.getProperty("spring.data.mongodb.host");
	  return mongoPrefix + user + ":" + pwd + "@" + host;
  }
  
  @Bean
  public MongoClient reactiveMongoClient() {
    return MongoClients.create(createConnectionString());
  }

  @Bean
  public ReactiveMongoTemplate reactiveMongoTemplate() {
    return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
  }

}
