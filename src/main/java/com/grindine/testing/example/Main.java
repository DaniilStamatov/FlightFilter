package com.grindine.testing.example;

import com.grindine.testing.Entity.Flight;
import com.grindine.testing.FlightBuilder.FlightBuilder;
import com.grindine.testing.FlightFilter.FlightFilter;
import com.grindine.testing.FlightFilter.DepartureBeforeNow;
import com.grindine.testing.FlightFilter.ArrivalIsAfterDeparture;
import com.grindine.testing.FlightFilter.FilterTimeOnGround;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        FlightFilter flightFilterDepartureBeforeNow = new DepartureBeforeNow();
        FlightFilter flightFilterArrivalIsAfterDeparture = new ArrivalIsAfterDeparture();
        FlightFilter filterTimeOnGround = new FilterTimeOnGround();

        List<Flight> timeOnGround = filterTimeOnGround.filter(flights);
        List<Flight> DepartureBeforeNow = flightFilterDepartureBeforeNow.filter(flights);
        List<Flight> second = flightFilterArrivalIsAfterDeparture.filter(flights);

        System.out.println(DepartureBeforeNow);
        System.out.println(second);
        System.out.println(timeOnGround);
    }
}