import com.grindine.testing.Entity.Flight;
import com.grindine.testing.Entity.Segment;
import com.grindine.testing.FlightFilter.FlightFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.grindine.testing.FlightFilter.DepartureBeforeNow;
import com.grindine.testing.FlightFilter.ArrivalIsAfterDeparture;
import com.grindine.testing.FlightFilter.FilterTimeOnGround;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BuilderTest {
    List<Flight> correctFlightsAfterNow;
    List<Flight> departureBeforeNow;
    List<Flight> arrivalIsBeforeDeparture;
    List<Flight> moreThanTwoHoursOnGroundFlights;
    List<Flight> testFlights;

    public void initNormalFlightsInAfterNow() {
        correctFlightsAfterNow = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now().plusHours(1);
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);

        List<Segment> correctSingleSegment = Collections.singletonList(new Segment(now, now.plusHours(4)));
        Flight normalSingleSegmentFlight = new Flight(correctSingleSegment);

        List<Segment> correctMultiSegments = new ArrayList<>();
        correctMultiSegments.add(new Segment(tomorrow, tomorrow.plusHours(4)));
        correctMultiSegments.add(new Segment(tomorrow.plusHours(5), tomorrow.plusHours(10)));
        correctMultiSegments.add(new Segment(tomorrow.plusHours(10).plusMinutes(30), tomorrow.plusHours(14)));
        Flight normalMultiSegmentFlight = new Flight(correctMultiSegments);

        correctFlightsAfterNow.add(normalSingleSegmentFlight);
        correctFlightsAfterNow.add(normalMultiSegmentFlight);
    }

    public void initDepartureBeforeNow(){
        departureBeforeNow = new ArrayList<>();

        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);
        List<Segment> singleSegment = Collections.singletonList(new Segment(yesterday, yesterday.plusHours(3)));
        Flight singleSegmentFlight = new Flight(singleSegment);

        List<Segment> multiSegment = new ArrayList<>();
        multiSegment.add(new Segment(threeDaysAgo, threeDaysAgo.plusHours(3)));
        multiSegment.add(new Segment(threeDaysAgo.plusHours(4), threeDaysAgo.plusHours(7)));
        multiSegment.add(new Segment(threeDaysAgo.plusHours(7).plusMinutes(30), threeDaysAgo.plusHours(10).plusMinutes(30)));
        Flight multiSegmentFlight = new Flight(multiSegment);

        departureBeforeNow.add(singleSegmentFlight);
        departureBeforeNow.add(multiSegmentFlight);
    }

    public void initArrivalIsBeforeDeparture(){
        arrivalIsBeforeDeparture = new ArrayList<>();
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);

        List<Segment> singleSegment = Collections.singletonList(new Segment(tomorrow, tomorrow.minusHours(3)));
        Flight singleSegmentFlight = new Flight(singleSegment);

        List<Segment> multiSegment = new ArrayList<>();
        multiSegment.add(new Segment(tomorrow, tomorrow.minusHours(3)));
        multiSegment.add(new Segment(tomorrow.minusHours(4), tomorrow.minusHours(7)));
        multiSegment.add(new Segment(tomorrow.minusHours(7).plusMinutes(30), tomorrow.minusHours(10)));

        Flight multiSegmentFlight = new Flight(multiSegment);

        arrivalIsBeforeDeparture.add(singleSegmentFlight);
        arrivalIsBeforeDeparture.add(multiSegmentFlight);
    }

    public void initMoreThanTwoHoursOnGround(){
        moreThanTwoHoursOnGroundFlights = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);

        List<Segment> multiSegments = new ArrayList<>();
        multiSegments.add(new Segment(tomorrow, tomorrow.plusHours(4)));
        multiSegments.add(new Segment(tomorrow.plusHours(5), tomorrow.plusHours(10)));
        multiSegments.add(new Segment(tomorrow.plusHours(12), tomorrow.plusHours(14)));
        Flight multiSegmentFlight = new Flight(multiSegments);

        moreThanTwoHoursOnGroundFlights.add(multiSegmentFlight);

    }

    @BeforeEach
    public void initTestFlights(){
        testFlights = new ArrayList<>();

        initNormalFlightsInAfterNow();
        initDepartureBeforeNow();
        initArrivalIsBeforeDeparture();
        initMoreThanTwoHoursOnGround();

        testFlights.addAll(correctFlightsAfterNow);
        testFlights.addAll(departureBeforeNow);
        testFlights.addAll(moreThanTwoHoursOnGroundFlights);
        testFlights.addAll(arrivalIsBeforeDeparture);
    }

    @Test
    public void testFilterFlightsBeforeNow(){
        FlightFilter flightFilter = new DepartureBeforeNow();
        List<Flight> filteredFlights = flightFilter.filter(departureBeforeNow);

        assertTrue(filteredFlights.isEmpty());
    }

    @Test
    public void testFilterFlightsBeforeNowAllFlights(){
        FlightFilter flightFilter = new DepartureBeforeNow();
        List<Flight> expectedFlights = new ArrayList<>(testFlights);
        expectedFlights.removeAll(departureBeforeNow);

        List<Flight> filteredFlights = flightFilter.filter(testFlights);

        assertEquals(expectedFlights, filteredFlights);
    }

    @Test
    public void testFilterFlightsArrivalIsBeforeDeparture(){
        FlightFilter flightFilter = new ArrivalIsAfterDeparture();
        List<Flight> filteredFlights = flightFilter.filter(arrivalIsBeforeDeparture);

        assertTrue(filteredFlights.isEmpty());
    }

    @Test
    public void testFilterFlightsArrivalIsBeforeDepartureAllFlights(){
        FlightFilter flightFilter = new ArrivalIsAfterDeparture();
        List<Flight> expectedFlights = new ArrayList<>(testFlights);
        expectedFlights.removeAll(arrivalIsBeforeDeparture);

        List<Flight> filteredFlights = flightFilter.filter(testFlights);

        assertEquals(expectedFlights, filteredFlights);

    }

    @Test
    public void testFilterMoreThanTwoHoursOnGround(){
        FlightFilter flightFilter = new FilterTimeOnGround();
        List<Flight> filteredFlights = flightFilter.filter(moreThanTwoHoursOnGroundFlights);

        assertTrue(filteredFlights.isEmpty());
    }

    @Test
    public void testFilterMoreThanTwoHoursOnGroundAllFlights(){
        FlightFilter flightFilter = new FilterTimeOnGround();
        List<Flight> expectedFlights = new ArrayList<>(testFlights);
        expectedFlights.removeAll(moreThanTwoHoursOnGroundFlights);

        List<Flight> filteredFlights = flightFilter.filter(testFlights);

        assertEquals(expectedFlights, filteredFlights);

    }

    @Test
    public void testAllFlights(){
        List<Flight> expectedFlights = correctFlightsAfterNow;
        FlightFilter firstFilter = new FilterTimeOnGround();
        FlightFilter secondFilter = new ArrivalIsAfterDeparture();
        FlightFilter thirdFilter = new DepartureBeforeNow();

        List<Flight> filteredFlights = thirdFilter.filter(secondFilter.filter(firstFilter.filter(testFlights)));
        assertEquals(expectedFlights, filteredFlights);
    }
}
