package com.andronov.tickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class PassengerDTO implements Serializable {
    @NotNull(message = "Passenger infant status is required")
    private boolean isChild;
    @NotNull(message = "Passenger's luggage information is required")
    private int luggage;
}
