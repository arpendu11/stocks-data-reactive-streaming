package com.practice.config;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractReactiveCouchbaseConfiguration;
import org.springframework.data.couchbase.config.BeanNames;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;
import org.springframework.data.couchbase.repository.support.IndexManager;

import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

@Configuration
@EnableReactiveCouchbaseRepositories("com.practice.repositories")
public class ReactiveCouchbaseConfiguration extends AbstractReactiveCouchbaseConfiguration {

	private CouchbaseProperties couchbaseProperties;

	public ReactiveCouchbaseConfiguration(CouchbaseProperties couchbaseProperties) {
		this.couchbaseProperties = couchbaseProperties;
	}

	@Override
	protected List<String> getBootstrapHosts() {
		return couchbaseProperties.getBootstrapHosts();
	}

	@Override
	protected String getBucketName() {
		return couchbaseProperties.getBucketName();
	}

	@Override
	protected String getBucketPassword() {
		return couchbaseProperties.getBucketPassword();
	}

	@Override
	@ConditionalOnMissingBean(name = BeanNames.COUCHBASE_INDEX_MANAGER)
	@Bean(name = BeanNames.COUCHBASE_INDEX_MANAGER)
	public IndexManager indexManager() {
		return new IndexManager(true, true, true);
	}

	@Override
	public CouchbaseEnvironment couchbaseEnvironment() {
		return DefaultCouchbaseEnvironment.builder().bootstrapHttpDirectPort(couchbaseProperties.getPort()).build();
	}
}
