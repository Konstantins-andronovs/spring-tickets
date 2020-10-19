package com.andronov.tickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class PriceRequestDTO implements Serializable {
    private String terminalName;
}
