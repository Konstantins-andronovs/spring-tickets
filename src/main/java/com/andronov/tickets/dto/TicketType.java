package com.andronov.tickets.dto;

public enum TicketType {
    ADULT(0),
    CHILD(-1),
    LUGGAGE(-2);

    public final int value;

    private TicketType(int value) {
        this.value = value;
    }
}
