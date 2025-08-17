package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DeviceTypeDto {
    MOTION_SENSOR,
    TEMPERATURE_SENSOR,
    LIGHT_SENSOR,
    CLIMATE_SENSOR,
    SWITCH_SENSOR;

    @JsonCreator
    public static DeviceTypeDto from(String raw) {
        if (raw == null) return null;
        String v = raw.trim().toUpperCase();
        return switch (v) {
            case "MOTION", "MOTION_SENSOR" -> MOTION_SENSOR;
            case "TEMPERATURE", "TEMPERATURE_SENSOR" -> TEMPERATURE_SENSOR;
            case "LIGHT", "LIGHT_SENSOR" -> LIGHT_SENSOR;
            case "CLIMATE", "CLIMATE_SENSOR" -> CLIMATE_SENSOR;
            case "SWITCH", "SWITCH_SENSOR" -> SWITCH_SENSOR;
            default -> throw new IllegalArgumentException("Unknown deviceType: " + raw);
        };
    }
}