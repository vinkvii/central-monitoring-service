package com.warehouse.central.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SensorDto {
    private String sensorId;
    private int value;
    private String sensorType;
}
