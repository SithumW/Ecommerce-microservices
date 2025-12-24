package com.ecommerce.user.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Override
    protected String getDatabaseName() {
        // Use database name from properties
        return databaseName;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoUri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void printMongoUri() {
        System.out.println("===========================================");
        System.out.println("MongoDB URI from properties: " + mongoUri);
        System.out.println("Database name: " + getDatabaseName());
        System.out.println("===========================================");

        // Test and print all collections
        printCollections();
    }

    /**
     * Test function to print all collections in the database
     */
    public void printCollections() {
        try {
            MongoTemplate template = mongoTemplate();
            System.out.println("\n========== DATABASE COLLECTIONS ==========");
            System.out.println("Database: " + getDatabaseName());

            // Get all collection names
            var collections = template.getDb().listCollectionNames();

            int count = 0;
            for (String collectionName : collections) {
                count++;
                System.out.println(count + ". " + collectionName);
            }

            if (count == 0) {
                System.out.println("No collections found in database '" + getDatabaseName() + "'");
                System.out.println("Collections will be created automatically when you insert data.");
            } else {
                System.out.println("\nTotal collections: " + count);
            }

            System.out.println("==========================================\n");
        } catch (Exception e) {
            System.err.println("Error printing collections: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
