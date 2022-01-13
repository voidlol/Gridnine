package com.gridnine.testing.filter;

import com.gridnine.testing.Flight;
import com.gridnine.testing.FlightBuilder;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterModuleTest {


    /*
    Testing methodology:
        1) Get default flights list from static method createFlights()
        2) Manually create filtered list, which will only contains filtered results and name it expectedResult
        3) Perform a filter() method on default flights list. Save result in filteredFlights var
        4) Compare filteredFlights to expectedResult
     */

    private final List<Flight> flights = FlightBuilder.createFlights();
    private final LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);

    @Test
    void testFilterWhenDepartureIsBeforeNow() {
        List<Flight> expectedResult = new ArrayList<>();
        expectedResult.add(FlightBuilder.createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow));
        List<Flight> filteredFlights;
        filteredFlights = FilterModule.filter(flights, flight -> flight.getSegments().get(0).getDepartureDate().isBefore(LocalDateTime.now()));
        assertTrue(filteredFlights.equals(expectedResult));
    }

    @Test
    void testFilterWhenArrivalBeforeDeparture() {
        List<Flight> expectedResult = new ArrayList<>();
        expectedResult.add(FlightBuilder.createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)));
        List<Flight> filteredFlights;
        filteredFlights = FilterModule.filter(flights, flight -> flight.getSegments().stream().anyMatch(s -> s.getArrivalDate().isBefore(s.getDepartureDate())));
        assertTrue(filteredFlights.equals(expectedResult));
    }

    @Test
    void testFilterWhenTotalGroundTimeMoreThan2Hours() {
        List<Flight> expectedResult = new ArrayList<>();
        expectedResult.add(FlightBuilder.createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)));
        expectedResult.add(FlightBuilder.createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
        List<Flight> filteredFlights;
        filteredFlights = FilterModule.filter(flights, flight -> {
            Duration totalDuration = Duration.between(flight.getSegments().get(0).getDepartureDate(), flight.getSegments().get(flight.getSegments().size() - 1).getArrivalDate());
            Duration flyingDuration = flight.getSegments().stream()
                    .map(s -> Duration.between(s.getDepartureDate(), s.getArrivalDate()))
                    .reduce(Duration::plus)
                    .get();

            return totalDuration.minus(flyingDuration).compareTo(Duration.ofHours(2)) > 0;
        });
        assertTrue(filteredFlights.equals(expectedResult));
    }
}