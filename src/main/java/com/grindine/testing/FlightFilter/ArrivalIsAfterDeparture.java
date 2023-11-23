package com.grindine.testing.FlightFilter;

import com.grindine.testing.Entity.Flight;

import java.util.List;
import java.util.stream.Collectors;

public class ArrivalIsAfterDeparture implements FlightFilter{
    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights
                .stream()
                .filter(flight -> flight.getSegments()
                        .stream()
                        .noneMatch(segment -> segment.getDepartureDate().isAfter(segment.getArrivalDate())))
                .collect(Collectors.toList());
    }
}
