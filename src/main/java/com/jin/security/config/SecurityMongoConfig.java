package com.jin.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;

@Configuration
@ConfigurationProperties(prefix = "com.jin.security.userstore.mongodb")
@EnableMongoRepositories(basePackages = "com.jin.security.repository",  mongoTemplateRef = "securityMongoTemplate")
public class SecurityMongoConfig extends AbstractMongoConfiguration {


	private String uri;
	private String database;
	
	@Override
	protected String getDatabaseName() {
		
		return this.database;
	}

	@Override
	public MongoClient mongo() throws Exception {
		
		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
		builder.connectionsPerHost(10).connectTimeout(3);
		
		MongoClientURI mongoUri = new MongoClientURI(this.uri,builder);
		
		MongoClient mongo = new MongoClient(mongoUri);
		
		return mongo;
	}
	
	public MongoDbFactory securityMongoDbFactory() throws Exception {
		SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongo(), getDatabaseName());
		simpleMongoDbFactory.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		return simpleMongoDbFactory;
	}
	
	@Bean("securityMongoTemplate")
	public MongoTemplate securityMongoTemplate() throws Exception {
		return new MongoTemplate(securityMongoDbFactory(), mappingMongoConverter());
	}


	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}
	
	
	
	
}
