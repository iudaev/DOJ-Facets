package com.company.facets.app;

import com.company.facets.entity.Destinations;
import com.company.facets.entity.Flight;
import io.jmix.core.DataManager;
import io.jmix.core.EntitySet;
import io.jmix.core.SaveContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
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

    private final Random random = new Random();



    // Method to generate random flight numbers
    private List<String> generateFlightNumbers() {
        return IntStream.range(0, 7)
                .mapToObj(i -> "FL" + RandomStringUtils.randomNumeric(3) + RandomStringUtils.randomAlphabetic(2).toUpperCase())
                .collect(Collectors.toList());
    }

    // Method to generate flights information
    public int generateFlights() {
        List<String> flightNumbers = generateFlightNumbers();

        if (flightNumbers.isEmpty()) {
            throw new IllegalArgumentException("Flight numbers must have at least one element.");
        }

        List<Flight> flights = IntStream.range(0, flightNumbers.size())
                .mapToObj(i -> {
                    Flight flight = dataManager.create(Flight.class);
                    flight.setFlightNumber(flightNumbers.get(i)); // Set the flight number
                    flight.setDestination(getRandomDestination()); // Set a random destination from the enum
                    return flight;
                })
                .collect(Collectors.toList());

        EntitySet entitySet = dataManager.save(new SaveContext().saving(flights));

        return entitySet.size();
    }

    // Method to get a random destination from the FlightDestination enum
    private Destinations getRandomDestination() {
        Destinations[] destinations = Destinations.values();
        return destinations[random.nextInt(destinations.length)];
    }
}