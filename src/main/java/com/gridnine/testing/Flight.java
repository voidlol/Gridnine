package com.gridnine.testing;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Flight {

    private final List<Segment> segments;

    Flight(final List<Segment> segs) {
        segments = segs;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    @Override
    public String toString() {
        return segments.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return getSegments().equals(flight.getSegments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSegments());
    }
}
