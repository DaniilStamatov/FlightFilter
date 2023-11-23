package com.grindine.testing.FlightFilter;

import com.grindine.testing.Entity.Flight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DepartureBeforeNow implements FlightFilter{
    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights
                .stream()
                .filter(flight->flight.getSegments()
                        .stream()
                        .noneMatch(segment -> segment.getDepartureDate().isBefore(LocalDateTime.now())))
                .collect(Collectors.toList());
    }
}
