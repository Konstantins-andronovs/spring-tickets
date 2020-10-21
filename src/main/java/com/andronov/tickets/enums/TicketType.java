package com.andronov.tickets.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TicketType {
    ADULT("ADULT"),
    CHILD("CHILD"),
    LUGGAGE("LUGGAGE"),

    UNKNOWN_TO_SDK_VERSION(null);

    private final String value;

    TicketType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Use this in place of valueOf to convert the raw string returned by the service into the enum value.
     *
     * @param value real value
     * @return Example corresponding to the value
     */
    @JsonCreator
    public static TicketType fromValue(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(TicketType.values()).filter(e -> e.toString().equals(value)).findFirst()
                .orElse(UNKNOWN_TO_SDK_VERSION);
    }

    public static Set<TicketType> knownValues() {
        return Stream.of(values()).filter(v -> v != UNKNOWN_TO_SDK_VERSION).collect(Collectors.toSet());
    }
}
