package com.rainforesteco.spring.data.config;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig 
{
	@Autowired
    private Environment env;

    @Bean
    public MongoDatabaseFactory mongoDbFactory() 
    {	
    	String connectionUrl = env.getProperty("spring.data.mongodb.uri");
    	String database      = env.getProperty("spring.data.mongodb.database");
        MongoClient client = new MongoClientURI(connectionUrl);
        MongoDatabase db   = client.getDatabase(database);
                
        return new SimpleMongoDbFactory(db);
    }

	@Bean
	public MongoTemplate mongoTemplate() {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

		return mongoTemplate;
    }
}
