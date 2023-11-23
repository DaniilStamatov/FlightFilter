package com.grindine.testing.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Segment {
    private final LocalDateTime departureDate;

    private final LocalDateTime arrivalDate;

    public Segment(final LocalDateTime departureDate, final LocalDateTime arrivalDate) {
        this.departureDate = Objects.requireNonNull(departureDate);
        this.arrivalDate = Objects.requireNonNull(arrivalDate);
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return '[' + departureDate.format(fmt) + '|' + arrivalDate.format(fmt)
                + ']';
    }
}
