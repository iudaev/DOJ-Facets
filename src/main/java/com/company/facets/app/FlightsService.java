package com.company.facets.app;

import com.company.facets.entity.Flight;
import io.jmix.core.DataManager;
import io.jmix.core.EntitySet;
import io.jmix.core.SaveContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class FlightsService {

    private final DataManager dataManager;

    public FlightsService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    // Method to generate flight numbers
    private List<String> generateFlightNumbers() {
        // Example implementation for generating flight numbers
        return IntStream.range(0, 10)
                .mapToObj(i -> "FL" + RandomStringUtils.randomNumeric(3) + RandomStringUtils.randomAlphabetic(2).toUpperCase())
                .collect(Collectors.toList());
    }

    // Method to generate flight destinations
    private List<String> generateFlightDestinations() {
        // Example implementation for generating flight destinations
        return List.of("London", "Berlin", "New Delhi", "Dallas", "Beijing", "Paris", "Canberra", "Ottawa", "Cairo", "Rome");
    }

    // Method to generate flights information
    public int generateFlights() {
        List<String> flightNumbers = generateFlightNumbers();
        List<String> flightDestinations = generateFlightDestinations();

        // Ensure both lists have the same size
        if (flightNumbers.size() != flightDestinations.size()) {
            throw new IllegalArgumentException("Flight numbers and destinations must have the same number of elements.");
        }

        List<Flight> flights = IntStream.range(0, flightNumbers.size())
                .mapToObj(i -> {
                    Flight flight = dataManager.create(Flight.class);
                    flight.setFlightNumber(flightNumbers.get(i)); // Set the flight number
                    flight.setDestination(flightDestinations.get(i)); // Set the destination
                    return flight;
                })
                .collect(Collectors.toList());

        EntitySet entitySet = dataManager.save(new SaveContext().saving(flights));

        return entitySet.size();
    }

}