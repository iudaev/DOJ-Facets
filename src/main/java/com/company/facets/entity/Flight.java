package com.company.facets.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@JmixEntity
@Table(name = "FLIGHT")
@Entity
public class Flight {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "FLIGHT_NUMBER")
    private String flightNumber;

    @Column(name = "DESTINATION")
    private String destination;

    public Destinations getDestination() {
        return destination == null ? null : Destinations.fromId(destination);
    }

    public void setDestination(Destinations destination) {
        this.destination = destination == null ? null : destination.getId();
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}