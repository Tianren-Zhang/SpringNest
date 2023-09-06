package com.laioffer.springnest;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;

@Service
public class ElasticsearchTester {

    @Autowired
    private RestHighLevelClient client;

    public void testConnection() throws Exception {
        // Ping Elasticsearch
        System.out.println(111);
        boolean isAvailable = client.ping(RequestOptions.DEFAULT);
        System.out.println("Is Elasticsearch Available? " + isAvailable);

        // Fetch Cluster Health
        ClusterHealthRequest healthRequest = new ClusterHealthRequest();
        ClusterHealthResponse healthResponse = client.cluster().health(healthRequest, RequestOptions.DEFAULT);
        System.out.println("Cluster Name: " + healthResponse.getClusterName() + ", Status: " + healthResponse.getStatus().name());

        // Fetch Basic Info
        MainResponse infoResponse = client.info(RequestOptions.DEFAULT);
        System.out.println("Cluster Name: " + infoResponse.getClusterName());
        System.out.println("Node Name: " + infoResponse.getNodeName());
        System.out.println("Version: " + infoResponse.getVersion().toString());
    }
}


