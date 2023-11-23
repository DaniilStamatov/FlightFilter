package com.grindine.testing.FlightFilter;

import com.grindine.testing.Entity.Flight;
import com.grindine.testing.Entity.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FilterTimeOnGround implements FlightFilter{
    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights.stream().filter(flight -> {
            List<Segment> segments = flight.getSegments();
            LocalDateTime nextDeparture;
            LocalDateTime lastArrival;
            Duration duration = Duration.ZERO;

            for(int i=1; i<segments.size(); i++){
                nextDeparture = segments.get(i).getDepartureDate();
                lastArrival = segments.get(i-1).getArrivalDate();
                duration = duration.plus(Duration.between(nextDeparture, lastArrival).abs());
            }
            return duration.toHours()<=2;})
                .collect(Collectors.toList());
    }
}
