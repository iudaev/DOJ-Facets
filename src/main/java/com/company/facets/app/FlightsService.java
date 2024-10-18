package com.company.facets.app;

import com.company.facets.entity.Destinations;
import com.company.facets.entity.Flight;
import com.company.facets.entity.Terminal;
import io.jmix.core.DataManager;
import io.jmix.core.EntitySet;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class FlightsService {

    private final DataManager dataManager;
    private final Random random = new Random();

    public FlightsService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    // Method to generate random flight numbers
    private List<String> generateFlightNumbers() {
        return IntStream.range(0, 7)
                .mapToObj(__ -> getRandomFlightNumber())
                .toList();
    }

    // Method to generate flights information
    public int generateFlights() {
        List<String> flightNumbers = generateFlightNumbers();

        if (flightNumbers.isEmpty()) {
            throw new IllegalStateException("Flight numbers must have at least one element");
        }

        List<Flight> flights = flightNumbers.stream()
                .map(flightNumber -> {
                    Flight flight = dataManager.create(Flight.class);
                    flight.setFlightNumber(flightNumber); // Set the flight number
                    flight.setDestination(getRandomDestination()); // Set a random destination from the enum
                    flight.setTerminal(getRandomTerminal());
                    return flight;
                })
                .toList();

        EntitySet entitySet = dataManager.saveAll(flights);
        return entitySet.size();
    }

    // Method to get a random destination from the FlightDestination enum
    private Destinations getRandomDestination() {
        Destinations[] destinations = Destinations.values();
        return destinations[random.nextInt(destinations.length)];
    }

    // Method to get a random flight number string
    private String getRandomFlightNumber() {
        return "FL" + RandomStringUtils.randomNumeric(3) + RandomStringUtils.randomAlphabetic(2).toUpperCase();
    }

    // Method to generate three A, B, and C terminals
    public void generateTerminals() {
        List<Terminal> terminals = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Terminal terminal = dataManager.create(Terminal.class);
            terminal.setName(String.valueOf((char) ('A' + i))); // Set terminal name to "A", "B", "C"
            terminals.add(terminal);
        }
        EntitySet entitySet = dataManager.saveAll(terminals);
    }


    private Terminal getRandomTerminal() {
        List<Terminal> terminals = dataManager.load(Terminal.class).all().list();
        if (terminals.isEmpty()) {
            generateTerminals();
        }
        Random random = new Random();
        return terminals.get(random.nextInt(terminals.size()));
    }


}