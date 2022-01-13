package com.gridnine.testing.filter;

import com.gridnine.testing.Flight;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterModule {

    public static List<Flight> filter(List<Flight> flights, Predicate<Flight>... rules) {
        return flights.stream()
                .filter(Arrays.stream(rules).reduce(rule -> true, Predicate::and))
                .collect(Collectors.toList());
    }
}
