package com.grindine.testing.FlightFilter;

import com.grindine.testing.Entity.Flight;

import java.util.List;

public interface FlightFilter {
    List<Flight> filter(List<Flight> flights);
}
