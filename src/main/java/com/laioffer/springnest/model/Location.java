package com.laioffer.springnest.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

// The mode of Location that intended to be indexed into an Elasticsearch instance.
@Document(indexName = "loc")
public class Location {


    @Id
    @Field(type = FieldType.Long)
    private Long id;

    // This annotation marks the field as a geographical point which will be indexed into Elasticsearch as a geo point type, allowing for spatial searches
    @GeoPointField
    private GeoPoint geoPoint;


    public Location(Long id, GeoPoint geoPoint) {
        this.id = id;
        this.geoPoint = geoPoint;
    }


    public Long getId() {
        return id;
    }


    public GeoPoint getGeoPoint() {
        return geoPoint;
    }
}

