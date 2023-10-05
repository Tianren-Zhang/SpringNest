package com.laioffer.springnest.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;


@Configuration
public class ElasticsearchConfig {


    @Value("${elasticsearch.address}")
    private String elasticsearchAddress;


    @Value("${elasticsearch.username}")
    private String elasticsearchUsername;


    @Value("${elasticsearch.password}")
    private String elasticsearchPassword;

    /*
    * A ClientConfiguration object is created using its builder pattern.
    * This configuration object will contain details about how to connect to the Elasticsearch cluster.
    * .connectedTo(elasticsearchAddress) connects the client to the provided Elasticsearch address.
    * .withBasicAuth(elasticsearchUsername, elasticsearchPassword) sets basic authentication for connecting to the Elasticsearch cluster.
    * RestClients.create(clientConfiguration).rest() creates and returns an instance of RestHighLevelClient using the given configuration.
    */
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticsearchAddress)
                .withBasicAuth(elasticsearchUsername, elasticsearchPassword)
                .build();


        return RestClients.create(clientConfiguration).rest();
    }
}

