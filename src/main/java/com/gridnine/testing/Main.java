package com.gridnine.testing;

import com.gridnine.testing.filter.FilterModule;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        List<Flight> filteredFlights;

        //Filter flights - departure before now
        filteredFlights = FilterModule.filter(flights, flight -> flight.getSegments().get(0).getDepartureDate().isBefore(LocalDateTime.now()));
        filteredFlights.forEach(System.out::println);

        //Filter flights - have segments with arrival before departure
        filteredFlights = FilterModule.filter(flights, flight -> flight.getSegments().stream().anyMatch(s -> s.getArrivalDate().isBefore(s.getDepartureDate())));
        filteredFlights.forEach(System.out::println);

        //Filter flights - total ground time > 2 hours
        filteredFlights = FilterModule.filter(flights, flight -> {
            Duration totalDuration = Duration.between(flight.getSegments().get(0).getDepartureDate(), flight.getSegments().get(flight.getSegments().size() - 1).getArrivalDate());
            Duration flyingDuration = flight.getSegments().stream()
                    .map(s -> Duration.between(s.getDepartureDate(), s.getArrivalDate()))
                    .reduce(Duration::plus)
                    .get();

            return totalDuration.minus(flyingDuration).compareTo(Duration.ofHours(2)) > 0;
        });
        filteredFlights.forEach(System.out::println);
    }
}
